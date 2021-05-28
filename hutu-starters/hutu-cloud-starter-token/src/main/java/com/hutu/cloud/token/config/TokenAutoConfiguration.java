package com.hutu.cloud.token.config;

import com.hutu.cloud.token.properties.TokenProperties;
import com.hutu.cloud.token.util.JwtUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * token自动配置
 *
 * @author hutu
 * @date 2021/4/1 9:50 上午
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(TokenProperties.class)
public class TokenAutoConfiguration implements WebMvcConfigurer {

	@Bean
	public JwtUtil jwtUtil(TokenProperties tokenProperties) {
		JwtUtil jwtUtil = new JwtUtil();
		jwtUtil.setTokenProperties(tokenProperties);
		return jwtUtil;
	}

}
