package com.hutu.common.core.util;

import com.alibaba.fastjson.JSON;
import com.hutu.common.core.entity.CallerInfo;
import com.hutu.common.core.enums.ErrorMsgEnum;
import com.hutu.common.core.exception.GlobalException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

/**
 * jwt工具类
 *
 * @author hutu
 * @date 2018/4/2 16:41
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
     * 生成jwt token
     */
    public static String generateToken(CallerInfo callerInfo) {
        return createToken(callerInfo, EXPIRE_TIME,false);
    }

    /**
     * 生成RefreshToken
     */
    public static String generateRefreshToken(CallerInfo callerInfo) {
        return createToken(callerInfo, REFRESH_EXPIRE_TIME,true);
    }

    private static String createToken(CallerInfo callerInfo, long expireTime, boolean isRefreshToken) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expireTime);
        String subject = JSON.toJSONString(callerInfo);
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
    public static Claims getClaimByToken(String token) {
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

