package com.hutu.common.core.util;

import com.alibaba.fastjson.JSON;
import com.hutu.common.core.enums.ErrorMsgEnum;
import com.hutu.common.core.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

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
    /**
     * 过期时间 1小时
     */
    private final static long EXPIRE_TIME = 1000 * 60 * 60;

    /**
     * 生成 jwt token
     */
    public static String createToken(Object sourceToken) {
        //发布时间
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
        String subject = JSON.toJSONString(sourceToken);
        String token = Jwts.builder()
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
            return claims;
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            long expire = ((Integer) claims.get("exp")).longValue();
            long difference = System.currentTimeMillis() - expire*1000;
            if (EXPIRE_TIME > difference) {
                throw new GlobalException(ErrorMsgEnum.TOKEN_NEED_REFRESH, e);
            }
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_EXPIRE, e);
        } catch (Exception e) {
            throw new GlobalException(e);
        }
    }

    /**
     * 刷新Token
     */
    public static String refreshToken(String token) {
        Claims claims = null;
        try {
             claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
             claims = e.getClaims();
        } catch (Exception e) {
            throw new GlobalException(e);
        }

        long expire = ((Integer) claims.get("exp")).longValue();
        long difference = System.currentTimeMillis() - expire*1000;
        if (EXPIRE_TIME > difference) {
            Date nowDate = new Date();
            Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
            return Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setClaims(claims)
                    .setIssuedAt(nowDate)
                    .setExpiration(expireDate)
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                    .compact();
        }
        throw new GlobalException(ErrorMsgEnum.TOKEN_IS_EXPIRE);
    }

    public static void main(String[] args) {
//        String token = createToken("123");
//        System.out.println(token);
        String token = refreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJcIjEyM1wiIiwiaWF0IjoxNTY0MDQ1NzIzLCJleHAiOjE1NjQwNDkzMjN9.aYe2pr7TY-rrGih6o9zXbUoi4znoHzQh5ZaxqbsqLD0");
        System.out.println(parseToken(token).toString());
    }
}
