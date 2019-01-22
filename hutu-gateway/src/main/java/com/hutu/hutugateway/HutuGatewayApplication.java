package com.hutu.hutugateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HutuGatewayApplication {

//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		StripPrefixGatewayFilterFactory.Config config = new StripPrefixGatewayFilterFactory.Config();
//		config.setParts(1);
//		return builder.routes()
//				.route("host_route", r -> r.path("/a/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8081"))
//				.route("host_route", r -> r.path("/b/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8082"))
//				.build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(HutuGatewayApplication.class, args);
	}

}
