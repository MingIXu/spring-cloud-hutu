package com.hutu.cloud.log.config;

import com.hutu.cloud.log.aspect.ApiLogAspect;
import com.hutu.cloud.core.constant.CommonConstant;
import com.hutu.cloud.log.event.ApiLogListener;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志工具自动配置
 *
 * @author hutu
 */
@Order
@EnableAsync
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableFeignClients(CommonConstant.BASE_PACKAGES)
@ConditionalOnWebApplication
public class LogAutoConfiguration {

	private final ApiLogService apiLogService;

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public ApiLogListener sysLogListener() {
		return new ApiLogListener(apiLogService);
	}

}
