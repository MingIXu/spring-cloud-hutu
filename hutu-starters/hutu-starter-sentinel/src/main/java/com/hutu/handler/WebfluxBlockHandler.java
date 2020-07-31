package com.hutu.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.hutu.core.R;
import com.hutu.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * sentinel统一降级限流策略 支持webflux模式
 * <p>
 * {@link com.alibaba.csp.sentinel.adapter.spring.webflux.callback.DefaultBlockRequestHandler}
 *
 * @author hutu
 * @date 2020/7/1 2:57 下午
 */
@Slf4j
public class WebfluxBlockHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(R.error(ResultCode.SERVICE_IS_BUSY.code, StrUtil.isNotEmpty(throwable.getMessage()) ? throwable.getMessage() : ResultCode.SERVICE_IS_BUSY.msg)));
    }
}
