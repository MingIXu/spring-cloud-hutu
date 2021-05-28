package com.hutu.cloud.feign.sentinel.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 默认限流熔断策略配置
 *
 * @author hutu
 * @date 2021/3/26 3:00 下午
 */
@Setter
@Getter
@ConfigurationProperties(prefix = SentinelProperties.PREFIX)
public class SentinelProperties {

	final static String PREFIX = "hutu.sentinel";

	@NestedConfigurationProperty
	private DegradeRuleProperties degrade;

	@NestedConfigurationProperty
	private FlowRuleProperties flow;

}
