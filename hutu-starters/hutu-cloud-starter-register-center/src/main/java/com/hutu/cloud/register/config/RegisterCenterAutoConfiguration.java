package com.hutu.cloud.register.config;

import com.alibaba.cloud.nacos.registry.NacosRegistrationCustomizer;
import com.hutu.cloud.register.discovery.MetadataRegistrationCustomizer;
import com.hutu.cloud.register.loadbalancer.CustomLoadBalancerClientConfiguration;
import com.hutu.cloud.register.properties.GrayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册中心自动配置
 *
 * @author hutu
 * @date 2021/4/20 2:04 下午
 */
@Configuration(proxyBeanMethods = false)
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerClientConfiguration.class)
@EnableConfigurationProperties(GrayProperties.class)
public class RegisterCenterAutoConfiguration {

	/**
	 * 注入自定义 NacosRegistration
	 * @return
	 */
	@Bean
	public NacosRegistrationCustomizer nacosRegistrationCustomizer() {
		return new MetadataRegistrationCustomizer();
	}

}
