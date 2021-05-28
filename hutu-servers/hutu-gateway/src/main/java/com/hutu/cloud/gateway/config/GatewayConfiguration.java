package com.hutu.cloud.gateway.config;

import com.hutu.cloud.gateway.props.AuthProperties;
import com.hutu.cloud.gateway.util.SignUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置
 *
 * @author hutu
 * @date 2021/5/7 10:02 上午
 */
@EnableConfigurationProperties(AuthProperties.class)
@Configuration
public class GatewayConfiguration {

	@Bean
	public SignUtil signUtil(AuthProperties authProperties) {
		return new SignUtil(authProperties);
	}

}
