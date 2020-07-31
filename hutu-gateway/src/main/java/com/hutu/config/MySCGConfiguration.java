package com.hutu.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.hutu.core.R;
import com.hutu.core.enums.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 限流后的处理类
 *
 * @author hutu
 * @date 2020/5/22 10:13 上午
 */
@Configuration
public class MySCGConfiguration {

	@Bean
	public BlockRequestHandler blockRequestHandler() {
		return new BlockRequestHandler() {
			@Override
			public Mono<ServerResponse> handleRequest(ServerWebExchange exchange,
					Throwable t) {
				return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(R.error(ResultCode.SERVICE_IS_BUSY.code, StrUtil.isNotEmpty(t.getMessage())?t.getMessage():ResultCode.SERVICE_IS_BUSY.msg)));
			}
		};
	}

}
