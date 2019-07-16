package com.hutu.auth.util;

import com.alibaba.fastjson.JSON;
import com.hutu.auth.enums.ErrorMsgEnum;
import com.hutu.auth.exception.GlobalException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

/**
 * jwt 工具类
 *
 * @author hutu
 * @date 2019/7/10 13:44
 */
public class JwtUtils {

    /**
     * 签名key
     */
    private final static String KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY.getBytes());

    private static final String IS_REFRESH_TOKEN = "isRefreshToken";
    /**
     * 过期时间 16小时
     */
    private final static long EXPIRE_TIME = 1000 * 60 * 60 * 16L;
    /**
     * 更新token过期时间 15天
     */
    private final static long REFRESH_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 15L;

    /**
     * 生成 jwt token
     */
    public static String generateToken(Object sourceToken) {
        return createToken(sourceToken, EXPIRE_TIME, false);
    }

    /**
     * 生成 RefreshToken
     */
    public static String generateRefreshToken(Object sourceToken) {
        return createToken(sourceToken, REFRESH_EXPIRE_TIME, true);
    }

    private static String createToken(Object sourceToken, long expireTime, boolean isRefreshToken) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expireTime);
        String subject = JSON.toJSONString(sourceToken);
        JwtBuilder builder = Jwts.builder();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(IS_REFRESH_TOKEN, isRefreshToken);
        builder.setClaims(hashMap);
        String token = builder
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    /**
     * 解析jwt token
     */
    public static Claims parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims != null && (boolean) claims.get(IS_REFRESH_TOKEN) ? null : claims;
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_EXPIRE, e);
        } catch (Exception e) {
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_INVALID, e);
        }
    }

    /**
     * 生成RefreshToken
     */
    public static String refreshToken(String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims == null) {
                return null;
            }
            claims.put(IS_REFRESH_TOKEN, false);
            Date nowDate = new Date();
            //过期时间
            Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
            JwtBuilder builder = Jwts.builder();
            String newToken = builder.setIssuedAt(nowDate)
                    .setExpiration(expireDate)
                    .setClaims(claims)
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                    .compact();
            return newToken;
        } catch (ExpiredJwtException e) {
            throw new GlobalException("refresh token is invalid !", e);
        } catch (Exception e) {
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_INVALID, e);
        }
    }
}
