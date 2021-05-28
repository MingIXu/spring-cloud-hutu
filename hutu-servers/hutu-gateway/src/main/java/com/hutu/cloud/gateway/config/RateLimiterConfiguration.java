package com.hutu.cloud.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * ip地址限流key
 *
 * @author hutu
 * @date 2021/4/19 11:49 上午
 */
@Configuration
public class RateLimiterConfiguration {

	@Bean(value = "ipAddrKeyResolver")
	public KeyResolver ipAddrKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
	}

}
