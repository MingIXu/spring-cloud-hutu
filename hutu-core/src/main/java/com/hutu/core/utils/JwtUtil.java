
package com.hutu.core.utils;

import cn.hutool.json.JSONUtil;
import com.hutu.core.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Jwt工具类
 *
 * @author hutu
 * @date 2019-12-13 9:29
 */
@Slf4j
public class JwtUtil {
    /**
     * 签名key
     */
    private final static String SECRET_KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

    /**
     * 生成 jwt token
     */
    public static String createToken(Object sourceToken, long expire) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(JSONUtil.toJsonStr(sourceToken))
                .setIssuedAt(new Date())
                .setExpiration(new Date(expire))
                .setId(IdGenerator.getIdStr())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 解析jwt token
     *
     * @param sourceToken 原token
     * @return token
     */
    public static Claims parseToken(String sourceToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(sourceToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new GlobalException("令牌token过期", e);
        } catch (Exception e) {
            throw new GlobalException("解析token失败", e);
        }
    }


    /**
     * 刷新Token
     *
     * @param sourceToken 原token
     * @param expire      过期时间
     * @return token
     */
    public static String refreshToken(String sourceToken, long expire) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(parseToken(sourceToken))
                .setIssuedAt(new Date())
                .setExpiration(new Date(expire))
                .setId(IdGenerator.getIdStr())
                .signWith(SignatureAlgorithm.HS512, "SECRET_KEY")
                .compact();
    }
}
