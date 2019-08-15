package com.hutu.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hutu.common.core.enums.ErrorMsgEnum;
import com.hutu.common.core.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * jwt 工具类
 *
 * @author hutu
 * @date 2019/7/10 13:44
 */
public class JwtUtils {

    private final static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    /**
     * 签名key
     */
    private final static String KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY.getBytes());
    /**
     * 可刷新时间段1小时到2小时之间
     */
    private final static long REFRESH_TIME = 1000 * 60 * 60;
    /**
     * 过期时间1小时
     */
    private final static long EXPIRE_TIME = 1000 * 60 * 60 * 2;

    /**
     * 生成 jwt token
     */
    public static String createToken(Object sourceToken) {
        String subject = JSON.toJSONString(sourceToken);
        //发布时间
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .setId(IdGenerator.getIdStr())
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    /**
     * 解析jwt token
     */
    public static Claims parseToken(String sourceToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(sourceToken)
                    .getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_EXPIRE, e);
        } catch (Exception e) {
            throw new GlobalException(e);
        }
    }

    /**
     * 刷新Token
     */
    public static String refreshToken(String token) {
        if (StrUtil.isEmpty(token)){
            throw new GlobalException(ErrorMsgEnum.NOT_FOUNT_TOKEN);
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            long issuedAt = claims.getIssuedAt().getTime();
            long difference = System.currentTimeMillis() - issuedAt;
            if (difference > REFRESH_TIME) {
                //发布时间
                Date nowDate = new Date();
                //过期时间
                Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
                return Jwts.builder()
                        .setHeaderParam("typ", "JWT")
                        .setClaims(claims)
                        .setIssuedAt(nowDate)
                        .setExpiration(expireDate)
                        .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                        .compact();
            } else {
                return null;
            }
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_EXPIRE, e);
        } catch (Exception e) {
            throw new GlobalException(e);
        }
    }

    public static void main(String[] args) {
//        String token = createToken("123");
//        System.out.println(token);
        String token = refreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJcIjEyM1wiIiwiaWF0IjoxNTY0MDQ1NzIzLCJleHAiOjE1NjQwNDkzMjN9.aYe2pr7TY-rrGih6o9zXbUoi4znoHzQh5ZaxqbsqLD0");
        System.out.println(parseToken(token).toString());
    }
}
