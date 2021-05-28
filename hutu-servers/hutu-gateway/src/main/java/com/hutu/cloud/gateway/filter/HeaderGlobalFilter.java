package com.hutu.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.core.constant.HeaderConstant;
import com.hutu.cloud.core.utils.SignHelper;
import org.springframework.beans.factory.annotation.Value;
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
public class HeaderGlobalFilter implements GlobalFilter, Ordered {

	@Value("${hutu.gray.type:''}")
	private String grayType;

	@Value("${hutu.gray.value:''}")
	private String grayValue;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
				/*
				 * // 追加链路追踪id .headers(h -> h.add(CommonConstant.TRACE_ID_HEADER,
				 * IdUtils.fastSimpleUUID())) // 追加isAuth标识 .headers(h ->
				 * h.add(CommonConstant.IS_AUTH, Boolean.TRUE.toString()))
				 */
				// 追加网关标识
				.headers(h -> h.add(HeaderConstant.CLIENT_ID_KEY, HeaderConstant.GATEWAY_CLIENT_ID))
				.headers(
						h -> h.add(HeaderConstant.CLIENT_SECRET_KEY, SignHelper.sign(HeaderConstant.GATEWAY_CLIENT_ID)))
				// 强制设置返回为json
				.headers(h -> h.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)))
				// 处理灰度条件请求头
				.headers(h -> {
					String grayType = h.getFirst(HeaderConstant.HEADER_GRAY_TYPE);
					String grayValue = h.getFirst(HeaderConstant.HEADER_GRAY_VALUE);
					if (StrUtil.hasEmpty(grayType, grayValue)) {
						if (!StrUtil.hasEmpty(this.grayType, this.grayValue)) {
							grayType = this.grayType;
							grayValue = this.grayValue;
						}
					}
					if (!StrUtil.hasEmpty(grayType, grayValue)) {
						h.set(HeaderConstant.HEADER_GRAY_TYPE, grayType);
						h.set(HeaderConstant.HEADER_GRAY_VALUE, grayValue);
					}
				}).build();
		ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
		return chain.filter(build);
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
