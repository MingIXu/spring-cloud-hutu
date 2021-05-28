package com.hutu.cloud.config;

import com.hutu.cloud.error.CustomBasicErrorController;
import com.hutu.cloud.error.CustomErrorAttributes;
import com.hutu.cloud.error.GlobalExceptionHandler;
import com.hutu.cloud.http.RestTemplateConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

/**
 * 引入controller全局异常处理和自定义404处理
 * <p>
 * ErrorMvcAutoConfiguration 会先配置 BasicErrorController导致 404 mapping重复 此处在
 * ErrorMvcAutoConfiguration 之前配置会导致 BasicErrorController 不装载。 详情看 BasicErrorController
 * 装载条件。
 *
 * @author hutu
 * @date 2019-12-11 16:17
 */
@Import({ CustomExecutorConfiguration.class, RestTemplateConfiguration.class, GlobalExceptionHandler.class })
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnWebApplication
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
public class CommonAutoConfiguration {

	private final ServerProperties serverProperties;

	/*
	 * @Bean public CustomFeignRequestHeaderInterceptor
	 * customFeignRequestHeaderInterceptor(){ return new
	 * CustomFeignRequestHeaderInterceptor(); }
	 */

	@Bean
	@ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
	public DefaultErrorAttributes errorAttributes() {
		return new CustomErrorAttributes();
	}

	@Bean
	@ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
	public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
		return new CustomBasicErrorController(errorAttributes, serverProperties.getError());
	}

}
