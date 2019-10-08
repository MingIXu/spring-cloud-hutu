package com.hutu.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.hutu.gateway.constant.GateConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author hutu
 * @create 2018/3/12.
 */
@Configuration
public class AccessGatewayFilter implements GlobalFilter {
    private final static Logger log = LoggerFactory.getLogger(AccessGatewayFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        requestUri = requestUri.substring(1);
        ServerHttpRequest.Builder mutate = request.mutate();

        log.info("requestUri: {}, method: {},params {}", requestUri, request.getMethodValue(), request.getQueryParams().toSingleValueMap());
        // 不进行拦截的地址
        if (isWhitePath(requestUri)) {
            ServerHttpRequest build = mutate.build();
            return chain.filter(exchange.mutate().request(build).build());
        }
        String token = exchange.getRequest().getHeaders().getFirst(GateConstant.TOKEN);
        HashMap<String, Object> map;
        // token不存在
//        if (StringUtils.isEmpty(token)) {
//            map = new HashMap<>();
//            map.put("code",500);
//            map.put("msg","not found token");
//            log.info(JSON.toJSONString(map));
//            return getVoidMono(exchange, map);
//        }

        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build());
    }



    /**
     * URI是否不过滤
     *
     * @param requestUri 请求方法uri
     * @return boolean
     */
    private boolean isWhitePath(String requestUri) {
        if (GateConstant.WHITE_PATHS.contains(requestUri)) {
            return true;
        }
        for (String s : GateConstant.START_WITH.split(GateConstant.DOU_HAO)) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 网关抛异常
     *
     * @param body
     */
    private Mono<Void> getVoidMono(ServerWebExchange serverWebExchange, Object body) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        byte[] bytes = JSON.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }
}
