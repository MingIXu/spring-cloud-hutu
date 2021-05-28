package com.hutu.cloud.gateway.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.hutu.cloud.core.R;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关限流降级自定义实现
 *
 * @author hutu
 * @date 2021/4/1 4:11 下午
 */
@Slf4j
@Component
public class GatewayBlockRequestHandler implements BlockRequestHandler {

	@Override
	public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable t) {
		log.error("gateway sentinel 降级 ", t);
		return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(R.failed(CommonStatusEnum.SERVICE_IS_BUSY.code,
						StrUtil.isNotEmpty(t.getMessage()) ? t.getMessage() : CommonStatusEnum.SERVICE_IS_BUSY.msg)));

	}

}