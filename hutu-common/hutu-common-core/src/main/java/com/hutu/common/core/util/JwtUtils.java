package com.hutu.common.core.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.hutu.common.core.enums.ErrorMsgEnum;
import com.hutu.common.core.exception.GlobalException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
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
            Claims claims = e.getClaims();
            Integer exp = (Integer) claims.get("exp");
            long currentSeconds = DateUtil.currentSeconds();

            if (REFRESH_EXPIRE_TIME > (currentSeconds - exp)) {
                refreshToken(claims);
            }
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_EXPIRE, e);
        } catch (Exception e) {
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_INVALID, e);
        }
    }

    /**
     * 生成RefreshToken
     */
    public static String refreshToken(Claims claims) {

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
    }

    public static void main(String[] args) {
//        String token = createToken("123", 10, false);
//        System.out.println(token);
        parseToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc1JlZnJlc2hUb2tlbiI6ZmFsc2UsInN1YiI6IlwiMTIzXCIiLCJpYXQiOjE1NjM1MjYwMDIsImV4cCI6MTU2MzUyNjAwMn0.Zc-ZQ8UyEUs5V3o1Ar0CCnUx_ydWnt-NjxFsqgxYgKI");
    }
}
