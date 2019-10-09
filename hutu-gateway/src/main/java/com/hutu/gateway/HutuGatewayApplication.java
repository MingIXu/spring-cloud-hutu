package com.hutu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HutuGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HutuGatewayApplication.class, args);
	}
}
