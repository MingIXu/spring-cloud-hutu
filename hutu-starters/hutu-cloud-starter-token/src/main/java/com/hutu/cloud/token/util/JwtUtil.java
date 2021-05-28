package com.hutu.cloud.token.util;

import com.hutu.cloud.core.exception.GlobalException;
import com.hutu.cloud.token.properties.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * Jwt工具类
 *
 * @author hutu
 * @date 2019-12-13 9:29
 */
@Slf4j
public class JwtUtil {

	/**
	 * base64的签名key
	 */
	private static TokenProperties tokenProperties;

	/**
	 * 解析jwt token
	 * @param sourceToken 原token
	 * @return token
	 */
	public static Claims parseToken(String sourceToken) {
		try {
			return Jwts.parser().setSigningKey(tokenProperties.getSecretKey()).parseClaimsJws(sourceToken).getBody();
		}
		catch (ExpiredJwtException e) {
			throw new GlobalException("令牌token过期", e);
		}
		catch (Exception e) {
			throw new GlobalException("解析token失败", e);
		}
	}

	public void setTokenProperties(TokenProperties tokenProperties) {
		JwtUtil.tokenProperties = tokenProperties;
	}

	protected static TokenProperties getTokenProperties() {
		return tokenProperties;
	}

}
