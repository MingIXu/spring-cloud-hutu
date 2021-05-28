package com.hutu.cloud.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hutu.cloud.core.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理
 *
 * @author hutu
 * @date 2021/4/1 4:54 下午
 */
@Slf4j
public class GlobalErrorExceptionHandler extends DefaultErrorWebExceptionHandler {

	private final ObjectMapper objectMapper;

	public GlobalErrorExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
			ErrorProperties errorProperties, ApplicationContext applicationContext, ObjectMapper objectMapper) {
		super(errorAttributes, resources, errorProperties, applicationContext);
		this.objectMapper = objectMapper;
	}

	@NonNull
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, @NonNull Throwable ex) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		if (response.isCommitted()) {
			return Mono.error(ex);
		}
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		if (ex instanceof ResponseStatusException) {
			response.setStatusCode(((ResponseStatusException) ex).getStatus());
		}
		return response.writeWith(Mono.fromSupplier(() -> {
			DataBufferFactory bufferFactory = response.bufferFactory();
			try {
				HttpStatus status = HttpStatus.BAD_GATEWAY;
				if (ex instanceof ResponseStatusException) {
					status = ((ResponseStatusException) ex).getStatus();
				}
				return bufferFactory
						.wrap(objectMapper.writeValueAsBytes(R.failed(status.value(), buildMessage(request, ex))));
			}
			catch (JsonProcessingException e) {
				e.printStackTrace();
				return bufferFactory.wrap(new byte[0]);
			}
		}));
	}

	/**
	 * 构建异常信息
	 */
	private String buildMessage(ServerHttpRequest request, Throwable ex) {
		StringBuilder message = new StringBuilder("Failed to handle request [");
		message.append(request.getMethodValue());
		message.append(" ");
		message.append(request.getURI());
		message.append("]");
		if (ex != null) {
			message.append(": ");
			message.append(ex.getMessage());
		}
		return message.toString();
	}

}
