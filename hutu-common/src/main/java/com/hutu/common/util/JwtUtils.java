package com.hutu.common.util;

import com.alibaba.fastjson.JSON;
import com.hutu.common.entity.CallerInfo;
import com.hutu.common.enums.ErrorMsgEnum;
import com.hutu.common.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * jwt工具类
 *
 * @author hutu
 * @date 2019/1/25
 */
public class JwtUtils {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 签名key
     */
    private final static String KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(KEY.getBytes());

    private final static Long HOUR = 16L;
    /**
     * 过期时间 16小时
     */
    private final static long EXPIRE_TIME = 1000 * 60 * 60 * HOUR;

    /**
     * 生成jwt token
     */
    public static String generateToken(CallerInfo callerInfo) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE_TIME);
        String subject = JSON.toJSONString(callerInfo);
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SECRET_KEY,SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    /**
     * 解析jwt token
     */
    public static Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_INVALID, e);
        }
    }

    /**
     * 获取用户id 此系统以用户id做subject
     *
     * @return subject
     */
    public static CallerInfo getCallerInfo() {
        String token = HttpContextUtils.getRequestToken();
        if (StringUtils.isNotEmpty(token)) {
            Claims claim = getClaimByToken(token);
            if (claim != null) {
                return JSON.parseObject(claim.getSubject(), CallerInfo.class);
            }
            throw new GlobalException(ErrorMsgEnum.TOKEN_IS_INVALID);
        } else {
            throw new GlobalException(ErrorMsgEnum.NOT_FOUNT_TOKEN);
        }
    }
}

