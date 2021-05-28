package com.hutu.cloud.register.loadbalancer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.hutu.cloud.core.constant.HeaderConstant;
import com.hutu.cloud.register.enums.GrayTypeEnum;
import com.hutu.cloud.register.properties.GrayProperties;
import com.hutu.cloud.register.util.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于灰度的负载均衡器
 *
 * @author hutu
 * @date 2021/4/20 2:26 下午
 */
@Slf4j
public class GrayRoundRobinLoadBalancer extends RoundRobinLoadBalancer {

	private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

	private String serviceId;

	private GrayProperties grayProperties;

	private AtomicInteger position;

	public GrayRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
			String serviceId, GrayProperties grayProperties) {
		super(serviceInstanceListSupplierProvider, serviceId);
		this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
		this.serviceId = serviceId;
		this.grayProperties = grayProperties;
		this.position = new AtomicInteger(new Random().nextInt(1000));
	}

	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
				.getIfAvailable(NoopServiceInstanceListSupplier::new);
		return supplier.get(request).next()
				.map(serviceInstances -> getInstanceResponse(serviceInstances, request, supplier));
	}

	Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request request,
			ServiceInstanceListSupplier supplier) {

		// 注册中心无可用实例 抛出异常
		if (CollUtil.isEmpty(instances)) {
			log.warn("No instance available {}", serviceId);
			return new EmptyResponse();
		}

		DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
		RequestData clientRequest = (RequestData) requestContext.getClientRequest();
		HttpHeaders headers = clientRequest.getHeaders();

		String grayType = headers.getFirst(HeaderConstant.HEADER_GRAY_TYPE);
		String grayValue = headers.getFirst(HeaderConstant.HEADER_GRAY_VALUE);
		// header 中没有则读取配置中的
		if (StrUtil.hasEmpty(grayType, grayValue) && StrUtil.isNotEmpty(grayProperties.getRegion())) {
			grayType = GrayTypeEnum.GROUP.getCode();
			grayValue = grayProperties.getRegion();
		}

		if (!StrUtil.hasEmpty(grayType, grayValue)) {

			ArrayList<ServiceInstance> filterInstances = new ArrayList<>();
			// 遍历可以实例元数据，若匹配则返回此实例
			for (ServiceInstance instance : instances) {
				NacosServiceInstance nacosInstance = (NacosServiceInstance) instance;
				Map<String, String> metadata = nacosInstance.getMetadata();
				if (grayValue.equalsIgnoreCase(metadata.get(EnumUtil.getEnumValue(GrayTypeEnum.class, grayType)))) {
					log.debug("gray request match success :type{} value{} instance{}", grayType, grayValue,
							nacosInstance);
					filterInstances.add(instance);
				}
			}
			if (filterInstances.size() != 0) {
				return processInstanceResponse(supplier, filterInstances);
			}
		}

		// 降级策略，使用轮询策略返回所有
		return super.choose(request).block();

	}

	private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
			List<ServiceInstance> serviceInstances) {
		Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
		if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
			((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
		}
		return serviceInstanceResponse;
	}

	/**
	 * copy from {@link RoundRobinLoadBalancer#getInstanceResponse}
	 * @param instances
	 * @return
	 */
	private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
		if (instances.isEmpty()) {
			if (log.isWarnEnabled()) {
				log.warn("No servers available for service: " + serviceId);
			}
			return new EmptyResponse();
		}
		// TODO: enforce order?
		int pos = Math.abs(this.position.incrementAndGet());

		ServiceInstance instance = instances.get(pos % instances.size());

		return new DefaultResponse(instance);
	}

}