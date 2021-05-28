package com.hutu.cloud.gateway;

import com.alibaba.cloud.sentinel.gateway.ConfigConstants;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		// 标记应用为网关,此处坑，alibaba未优化
		System.setProperty(SentinelConfig.APP_TYPE_PROP_KEY, ConfigConstants.APP_TYPE_SCG_GATEWAY);
		SpringApplication.run(GatewayApplication.class, args);
	}

}
