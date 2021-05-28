package com.hutu.cloud.token.service;

import com.hutu.cloud.token.enums.TokenTypeEnum;
import io.jsonwebtoken.Claims;

/**
 * @desc TokenService
 * @author hutu
 * @date 2021/3/30 10:06 下午
 */
public interface TokenService {

	default String create(String key, Object sourceToken, TokenTypeEnum tokenType) {
		return null;
	}

	default Claims parse(String sourceToken) {
		return null;
	}

	boolean save(String key, String token, long expire);

	boolean delete(String key);

	boolean update(String key, String newToken);

	String get(String key);

	long getTimeout(String key);

	void updateTimeout(String key, long timeout);

}
