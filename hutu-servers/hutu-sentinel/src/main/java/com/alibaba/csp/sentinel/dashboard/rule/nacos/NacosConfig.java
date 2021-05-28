package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @desc NacosConfig
 * @author hutu
 * @date 2020/12/2 5:32 下午
 */
@Configuration(proxyBeanMethods = false)
public class NacosConfig {

	@Bean
	public Converter<List<AuthorityRuleEntity>, String> authorityRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	@Bean
	public Converter<String, List<AuthorityRuleEntity>> authorityRuleEntityDecoder() {
		return s -> JSON.parseArray(s, AuthorityRuleEntity.class);
	}

	@Bean
	public Converter<List<ClusterAppAssignMap>, String> clusterAppAssignMapEncoder() {
		return JSON::toJSONString;
	}

	@Bean
	public Converter<String, List<ClusterAppAssignMap>> clusterAppAssignMapDecoder() {
		return s -> JSON.parseArray(s, ClusterAppAssignMap.class);
	}

	@Bean
	public Converter<List<ClusterClientConfig>, String> clusterClientConfigEncoder() {
		return JSON::toJSONString;
	}

	@Bean
	public Converter<String, List<ClusterClientConfig>> clusterClientConfigDecoder() {
		return s -> JSON.parseArray(s, ClusterClientConfig.class);
	}

	@Bean
	public Converter<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	@Bean
	public Converter<String, List<DegradeRuleEntity>> degradeRuleEntityDecoder() {
		return s -> JSON.parseArray(s, DegradeRuleEntity.class);
	}

	@Bean
	public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	@Bean
	public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
		return s -> JSON.parseArray(s, FlowRuleEntity.class);
	}

	@Bean
	public Converter<List<ParamFlowRuleEntity>, String> paramFlowRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	@Bean
	public Converter<String, List<ParamFlowRuleEntity>> paramFlowRuleEntityDecoder() {
		return s -> JSON.parseArray(s, ParamFlowRuleEntity.class);
	}

	@Bean
	public Converter<List<SystemRuleEntity>, String> systemRuleEntityEncoder() {
		return JSON::toJSONString;
	}

	@Bean
	public Converter<String, List<SystemRuleEntity>> systemRuleEntityDecoder() {
		return s -> JSON.parseArray(s, SystemRuleEntity.class);
	}

	@Bean
	public ConfigService nacosConfigService(NacosConfigManager nacosConfigManager) {
		return nacosConfigManager.getConfigService();
	}

}
