package com.hutu.cloud.register.discovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.hutu.cloud.register.filter.ServiceDiscoveryFilter;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 增强服务发现客户端
 *
 * @author hutu
 * @date 2021/4/21 10:37 上午
 */
public class CustomNacosServiceDiscovery extends NacosServiceDiscovery {

	private NacosDiscoveryProperties discoveryProperties;

	private NacosServiceManager nacosServiceManager;

	private ServiceDiscoveryFilter serviceDiscoveryFilter;

	public CustomNacosServiceDiscovery(NacosDiscoveryProperties discoveryProperties,
			NacosServiceManager nacosServiceManager, ServiceDiscoveryFilter serviceDiscoveryFilter) {
		super(discoveryProperties, nacosServiceManager);
		this.discoveryProperties = discoveryProperties;
		this.nacosServiceManager = nacosServiceManager;
		this.serviceDiscoveryFilter = serviceDiscoveryFilter;
	}

	@Override
	public List<ServiceInstance> getInstances(String serviceId) throws NacosException {
		String group = this.discoveryProperties.getGroup();
		List<Instance> instances = this.namingService().selectInstances(serviceId, group, true);

		return hostToServiceInstanceList(instances, serviceId);
	}

	private NamingService namingService() {
		return this.nacosServiceManager.getNamingService(this.discoveryProperties.getNacosProperties());
	}

}
