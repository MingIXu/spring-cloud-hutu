package com.hutu.filter;

import cn.hutool.core.util.StrUtil;
import com.hutu.core.R;
import com.hutu.core.constant.CommonConstant;
import com.hutu.core.constant.ProfilesConstant;
import com.hutu.core.constant.StringPool;
import com.hutu.core.enums.ResultCode;
import com.hutu.core.utils.JwtUtil;
import com.hutu.props.AuthProperties;
import com.hutu.provider.AuthProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

/**
 * token校验
 *
 * @author hutu
 * @date 2020/5/26 3:03 下午
 */
@Slf4j
@Order(-10)
@Component
public class AccessGatewayFilter implements GlobalFilter {

    private AuthProperties authProperties;
    private ObjectMapper objectMapper;

    @Value("${spring.profiles.active}")
    private String profile;

    AccessGatewayFilter(AuthProperties authProperties, ObjectMapper objectMapper) {
        this.authProperties = authProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("requestUri: {}, method: {},params {}", path, request.getMethodValue(), request.getQueryParams().toSingleValueMap());
        if (isSkip(path)||isDev()) {
            return chain.filter(exchange);
        }
        ServerHttpResponse resp = exchange.getResponse();
        String headerToken = exchange.getRequest().getHeaders().getFirst(CommonConstant.TOKEN);
        String paramToken = exchange.getRequest().getQueryParams().getFirst(CommonConstant.TOKEN);
        if (StrUtil.isAllBlank(headerToken, paramToken)) {
            return unAuth(resp, "缺失令牌,鉴权失败");
        }

        String token = StrUtil.isBlank(headerToken) ? paramToken : headerToken;
        JwtUtil.parseToken(token);
        return chain.filter(exchange);
    }

    /**
     * 判断是否为开发环境
     * @return boolean
     */
    private boolean isDev(){
        log.info("=========: "+profile);
        return ProfilesConstant.DEV.equalsIgnoreCase(profile);
    }

    /**
     * 处理白名单
     *
     * @param path 请求路径
     * @return boolean
     */
    private boolean isSkip(String path) {
        return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(StringPool.SLASH_DOUBLE_STAR, StringPool.EMPTY)).anyMatch(path::contains)
                || authProperties.getSkipUrl().stream().map(url -> url.replace(StringPool.SLASH_DOUBLE_STAR, StringPool.EMPTY)).anyMatch(path::contains);
    }

    private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String result = "";
        try {
            result = objectMapper.writeValueAsString(R.error(ResultCode.UNAUTHORIZED.code,msg));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }
}
