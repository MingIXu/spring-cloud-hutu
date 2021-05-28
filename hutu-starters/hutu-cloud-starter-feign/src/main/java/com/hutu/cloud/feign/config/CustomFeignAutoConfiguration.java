package com.hutu.cloud.feign.config;

import com.alibaba.cloud.circuitbreaker.sentinel.SentinelCircuitBreakerFactory;
import com.alibaba.cloud.circuitbreaker.sentinel.SentinelConfigBuilder;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.hutu.cloud.feign.properties.FeignProperties;
import com.hutu.cloud.feign.contract.CustomSpringMvcContract;
import com.hutu.cloud.feign.sentinel.handler.WebmvcBlockHandler;
import com.hutu.cloud.feign.sentinel.properties.SentinelProperties;
import com.hutu.cloud.feign.sentinel.proxy.CustomSentinelFeign;
import feign.Feign;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sentinel配置类
 *
 * @author hutu
 * @date 2020/5/21 4:45 下午
 */
@EnableConfigurationProperties({ SentinelProperties.class, FeignProperties.class })
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(FeignClientsConfiguration.class)
public class CustomFeignAutoConfiguration {

	@Autowired(required = false)
	private FeignClientProperties feignClientProperties;

	@Autowired(required = false)
	private List<AnnotatedParameterProcessor> parameterProcessors = new ArrayList<>();

	@Bean
	public Retryer feignRetry() {
		// period=100 发起当前请求的时间间隔,单位毫秒
		// maxPeriod=1000 发起当前请求的最大时间间隔,单位毫秒
		// maxAttempts=2 重试次数是1，因为包括第一次，所以我们如果想要重试2次，就需要设置为3
		Retryer retryer = new Retryer.Default(100, 1000, 2);
		return retryer;
	}

	@Bean
	public CustomSpringMvcContract getCustomSpringMvcContract(ConversionService feignConversionService) {
		boolean decodeSlash = feignClientProperties == null || feignClientProperties.isDecodeSlash();
		return new CustomSpringMvcContract(parameterProcessors, feignConversionService, decodeSlash);
	}

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder(Retryer retryer, FeignProperties feignProperties) {
		return CustomSentinelFeign.builder().feignProperties(feignProperties).retryer(retryer);
	}

	@Bean
	@ConditionalOnClass(HttpServletRequest.class)
	@ConditionalOnMissingBean
	public BlockExceptionHandler blockExceptionHandler() {
		return new WebmvcBlockHandler();
	}

	@Bean
	@ConditionalOnMissingBean({ CircuitBreakerFactory.class })
	public CircuitBreakerFactory sentinelCircuitBreakerFactory() {
		return new SentinelCircuitBreakerFactory();
	}

	/**
	 * 默认的熔断策略
	 */
	@Bean
	@ConditionalOnProperty(name = { "hutu.sentinel.degrade.enable" }, havingValue = "true")
	public Customizer<SentinelCircuitBreakerFactory> defaultConfig(SentinelProperties sentinelProperties) {
		return factory -> {
			factory.configureDefault(
					id -> new SentinelConfigBuilder().resourceName(id)
							.rules(Collections.singletonList(new DegradeRule(id)
									.setGrade(sentinelProperties.getDegrade().getGrade())
									.setCount(sentinelProperties.getDegrade().getCount())
									.setSlowRatioThreshold(sentinelProperties.getDegrade().getSlowRatioThreshold())
									.setMinRequestAmount(sentinelProperties.getDegrade().getMinRequestAmount())
									.setStatIntervalMs(sentinelProperties.getDegrade().getStatIntervalMs())
									.setTimeWindow(sentinelProperties.getDegrade().getTimeWindow())))
							.build());
		};
	}
	/*
	 * @Bean public Converter myConverter() { return new JsonFlowRuleListConverter(); }
	 */

	// 初始化一些系统配置或默认值
	/*
	 * @PostConstruct private void init() { if
	 * (StrUtil.isEmpty(System.getProperty("csp.sentinel.log.dir")) &&
	 * StringUtils.hasText("this.properties.getLog().getDir()")) {
	 * System.setProperty("csp.sentinel.log.dir", "this.properties.getLog().getDir()"); }
	 * }
	 */

}
