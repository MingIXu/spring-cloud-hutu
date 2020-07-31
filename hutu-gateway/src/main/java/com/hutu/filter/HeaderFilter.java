package com.hutu.filter;

import cn.hutool.core.util.IdUtil;
import com.hutu.core.constant.CommonConstant;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * 添加请求相关请求头
 *
 * @author hutu
 * @date 2020/5/26 2:50 下午
 */
@Component
public class  HeaderFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //链路追踪id
        String traceId = IdUtil.fastSimpleUUID();

        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
                // 追加链路追踪id
//                .headers(h -> h.add(CommonConstant.TRACE_ID_HEADER, traceId))
                // 追加网关标识
                .headers(h -> h.add(CommonConstant.GATEWAY_KEY, CommonConstant.GATEWAY_KEY_SIGN))
                // 追加isAuth标识
                .headers(h -> h.add(CommonConstant.IS_AUTH, Boolean.TRUE.toString()))
                // 强制设置返回为json
                .headers(h -> h.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)))
                .build();

        ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(build);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
