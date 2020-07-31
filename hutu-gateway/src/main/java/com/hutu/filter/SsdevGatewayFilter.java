package com.hutu.filter;

import cn.hutool.core.util.StrUtil;
import com.hutu.core.R;
import com.hutu.core.constant.ProfilesConstant;
import com.hutu.core.constant.SsdevConstant;
import com.hutu.core.constant.StringPool;
import com.hutu.provider.AuthProvider;
import com.hutu.core.enums.ResultCode;
import com.hutu.core.utils.SignHelper;
import com.hutu.props.AuthProperties;
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

/**
 * token校验
 *
 * @author hutu
 * @date 2020/5/26 3:03 下午
 */
@Slf4j
@Order(-20)
@Component
public class SsdevGatewayFilter implements GlobalFilter {

    private AuthProperties authProperties;
    private ObjectMapper objectMapper;

    @Value("${spring.profiles.active}")
    private String profile;

    SsdevGatewayFilter(AuthProperties authProperties, ObjectMapper objectMapper) {
        this.authProperties = authProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("requestUri: {}, method: {},params {}", path, request.getMethodValue(), request.getQueryParams().toSingleValueMap());
        if (isSkip(path)) {

            return chain.filter(exchange);
        }
        if (isDev()){

            return chain.filter(exchange);
        }
        ServerHttpResponse resp = exchange.getResponse();
        String headerSign = exchange.getRequest().getHeaders().getFirst(SsdevConstant.SIGN_KEY);
        String headerToken = exchange.getRequest().getHeaders().getFirst(SsdevConstant.TOKEN_KEy);
        String headerUid = exchange.getRequest().getHeaders().getFirst(SsdevConstant.UID);
        String signStr = SsdevConstant.SIGN_PREFIX+
                SsdevConstant.TOKEN_KEy+ StringPool.EQUALS+headerToken+
                SsdevConstant.UID+StringPool.EQUALS+headerUid;
        if (StrUtil.isBlank(headerToken)||SignHelper.verify(signStr,headerSign)) {
            return unAuth(resp, "缺失令牌,鉴权失败");
        }
        return chain.filter(exchange);
    }

    /**
     * 判断是否为开发环境
     * @return boolean
     */
    private boolean isDev(){
        log.info("开发环境放行所有请求: {}",profile);
        return ProfilesConstant.DEV.equalsIgnoreCase(profile);
    }

    /**
     * 处理白名单
     *
     * @param path 请求路径
     * @return boolean
     */
    private boolean isSkip(String path) {
        log.info("白名单放行：{}",path);
        return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(StringPool.ASTERISK, StringPool.EMPTY)).anyMatch(path::contains)
                || authProperties.getSkipUrl().stream().map(url -> url.replace(StringPool.ASTERISK, StringPool.EMPTY)).anyMatch(path::contains);
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
