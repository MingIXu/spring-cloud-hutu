package com.hutu.gateway.ratelimiter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求key策略
 *
 * @author hutu
 * @date 2019/1/19
 */
public class IpAddressKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "ipAddressKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
