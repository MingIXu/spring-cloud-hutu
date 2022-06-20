package com.hutu.cloud.passport.util;

import com.hutu.cloud.core.utils.JsonUtil;
import com.hutu.cloud.token.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * 只有授权中心才能创建 token
 *
 * @author hutu
 * @date 2021/4/1 10:08 上午
 */
public class AuthJwtUtil extends JwtUtil {

	/**
	 * 生成 jwt token
	 */
	public static String createToken(String key, Object sourceToken, long expire) {
		return Jwts.builder().setHeaderParam("typ", "JWT").setSubject(JsonUtil.toJson(sourceToken))
				.setIssuedAt(new Date()).setExpiration(new Date(expire + System.currentTimeMillis())).setId(key)
				.signWith(SignatureAlgorithm.HS512, getTokenProperties().getSecretKey()).compact();
	}

}
