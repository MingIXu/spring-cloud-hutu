package com.hutu.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.cache.util.RedisUtil;
import com.hutu.cloud.core.constant.ProfilesConstant;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.exception.GlobalException;
import com.hutu.cloud.gateway.constant.GatewayConstant;
import com.hutu.cloud.gateway.props.AuthProperties;
import com.hutu.cloud.gateway.provider.AuthProvider;
import com.hutu.cloud.token.constant.TokenConstant;
import com.hutu.cloud.token.enums.TokenStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局鉴权过滤器
 *
 * @author hutu
 * @date 2020/5/26 3:03 下午
 */
@Slf4j
@Order(-1)
@Component
public class AccessGlobalFilter implements GlobalFilter {

	private AuthProperties authProperties;

	private RedisUtil redisUtil;

	@Value("${spring.profiles.active}")
	private String profile;

	AccessGlobalFilter(AuthProperties authProperties, RedisUtil redisUtil) {
		this.authProperties = authProperties;
		this.redisUtil = redisUtil;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getURI().getPath();
		log.info("requestUri: {}, method: {},params {}", path, request.getMethodValue(),
				request.getQueryParams().toSingleValueMap());
		if (isSkip(path)) {
			return chain.filter(exchange);
		}
		String headerToken = request.getHeaders().getFirst(TokenConstant.HEADER_TOKEN_KEY);
		String paramToken = request.getQueryParams().getFirst(TokenConstant.HEADER_TOKEN_KEY);
		if (StrUtil.isAllBlank(headerToken, paramToken)) {
			throw new GlobalException(TokenStatusEnum.TOKEN_NOT_FOUNT_IN_REQUEST);
		}

		String token = StrUtil.isBlank(headerToken) ? paramToken : headerToken;
		// 能拿到说明没过期且合法
		Object jwtObject = redisUtil.get(GatewayConstant.CACHE_TOKEN_PREFIX + token);
		if (jwtObject == null) {
			throw new GlobalException(TokenStatusEnum.TOKEN_NOT_FOUNT_IN_SYSTEM);
		}
		return chain.filter(exchange);
	}

	/**
	 * 1. 处理白名单 2. 特殊环境不校验
	 * @param path 请求路径
	 * @return boolean
	 */
	private boolean isSkip(String path) {
		return isDev()
				|| AuthProvider.getDefaultSkipUrl().stream()
						.map(url -> url.replace(StringPool.ASTERISK, StringPool.EMPTY)).anyMatch(path::contains)
				|| authProperties.getSkipUrl().stream().map(url -> url.replace(StringPool.ASTERISK, StringPool.EMPTY))
						.anyMatch(path::contains);
	}

	/**
	 * 判断是否为开发环境
	 * @return boolean
	 */
	private boolean isDev() {
		log.info("=== env ===: " + profile);
		return ProfilesConstant.DEV.equalsIgnoreCase(profile);
	}

}
