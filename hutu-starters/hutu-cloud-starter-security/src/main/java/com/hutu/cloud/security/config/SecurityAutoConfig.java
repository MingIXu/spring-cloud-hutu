package com.hutu.cloud.security.config;

import com.hutu.cloud.security.aspect.AuthAspect;
import com.hutu.cloud.security.constant.SecurityConstant;
import com.hutu.cloud.security.endpoint.AppInfoEndpoint;
import com.hutu.cloud.security.interceptor.ClientInterceptor;
import com.hutu.cloud.security.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置类
 *
 * @author hutu
 * @date 2019/7/16 14:56
 */
@Order
@Configuration(proxyBeanMethods = false)
@ServletComponentScan("com.hutu.cloud.security.filter") // 添加过滤器
@AllArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
@Import({ AppInfoEndpoint.class, XssConfiguration.class })
public class SecurityAutoConfig implements WebMvcConfigurer {

	private final SecurityProperties securityProperties;

	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new AuthorizationInterceptor())
		// .excludePathPatterns(securityProperties.getSkipUrl())
		// .excludePathPatterns(SecurityConstant.DEFAULT_EXCLUDE_PATTERNS);
		registry.addInterceptor(new ClientInterceptor(securityProperties))
				.excludePathPatterns(securityProperties.getSkipUrl())
				.excludePathPatterns(SecurityConstant.DEFAULT_EXCLUDE_PATTERNS);
	}

	/**
	 * 注入鉴权aop
	 * @return AuthAspect
	 */
	@Bean
	public AuthAspect authAspect() {
		return new AuthAspect();
	}

}
