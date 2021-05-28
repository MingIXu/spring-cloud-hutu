package com.hutu.cloud.scheduler;

import com.hutu.cloud.scheduler.properties.XxlAdminProperties;
import com.hutu.cloud.scheduler.properties.XxlExecutorProperties;
import com.hutu.cloud.scheduler.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * xxl-job自动装配
 *
 * @author hutu
 * @date 2021/3/11 10:38 上午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ XxlAdminProperties.class, XxlExecutorProperties.class, XxlJobProperties.class })
@EnableAutoConfiguration
public class XxlJobAutoConfiguration {

	/**
	 * 服务名称 包含 XXL_JOB_ADMIN 则说明是 Admin
	 */
	private static final String XXL_JOB_ADMIN = "xxl-job-admin";

	/**
	 * 配置xxl-job 执行器，提供自动发现 xxl-job-admin 能力
	 * @param xxlJobProperties xxl 配置
	 * @param discoveryClient 注册发现客户端
	 * @return
	 */
	@Bean
	public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties,
			DiscoveryClient discoveryClient) {
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutor().getAppname());
		xxlJobSpringExecutor.setAddress(xxlJobProperties.getExecutor().getAddress());
		xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
		xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
		xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getExecutor().getAccessToken());
		xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
		xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());

		// 如果配置为空则获取注册中心的服务列表 "http://host:9080/xxl-job-admin"
		if (!StringUtils.hasText(xxlJobProperties.getAdmin().getAddresses())) {
			String serverList = discoveryClient.getServices().stream().filter(s -> s.contains(XXL_JOB_ADMIN))
					.flatMap(s -> discoveryClient.getInstances(s).stream()).map(instance -> String
							.format("http://%s:%s/%s", instance.getHost(), instance.getPort(), XXL_JOB_ADMIN))
					.collect(Collectors.joining(","));
			xxlJobSpringExecutor.setAdminAddresses(serverList);
		}
		else {
			xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
		}

		return xxlJobSpringExecutor;
	}

}
