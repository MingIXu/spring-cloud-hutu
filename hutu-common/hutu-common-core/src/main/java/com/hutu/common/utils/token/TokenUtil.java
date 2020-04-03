
package com.hutu.common.utils.token;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hutu.common.constant.CommonConstant;
import com.hutu.common.enums.ResultCode;
import com.hutu.common.exception.GlobalException;
import com.hutu.common.utils.IdGenerator;
import com.hutu.common.utils.StringPool;
import com.hutu.common.utils.WebUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * token工具类
 * @author hutu
 * @date 2019-12-13 9:29
 */
@Slf4j
public class TokenUtil {
    /**
     * 签名key
     */
    private final static String SECRET_KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

    /**
     * 生成 jwt token
     */
    public static TokenInfo createToken(Object sourceToken) {
        String subject = JSON.toJSONString(sourceToken);
        //发布时间
        Date nowDate = new Date();
        //过期时间
        long expire = getExpire();
        Date expireDate = new Date(expire);

        String compact = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .setId(IdGenerator.getIdStr())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        return new TokenInfo(compact,(int) expire / 1000);
    }

    /**
     * 解析jwt token
     */
    public static Claims parseToken(String sourceToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(sourceToken)
                    .getBody();
        } catch (ExpiredJwtException e){
            log.info("令牌token过期");
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            log.info("解析token失败");
            e.printStackTrace();
            throw new GlobalException("解析token失败", e);
        }
    }

    /**
     * 刷新Token
     */
    public static TokenInfo refreshToken() {

        String token = getTokenString();

        if (StrUtil.isEmpty(token)) {
            log.info("请求中无token认证信息");
            throw new GlobalException(ResultCode.UNAUTHORIZED);
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            //发布时间
            Date nowDate = new Date();
            //过期时间
            long expire = getExpire();
            Date expireDate = new Date(expire);
            String compact = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setClaims(claims)
                    .setIssuedAt(nowDate)
                    .setExpiration(expireDate)
                    .setId(IdGenerator.getIdStr())
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
            return new TokenInfo(compact, (int) expire / 1000);
        } catch (Exception e) {
            log.info("刷新Token失败");
            e.printStackTrace();
            throw new GlobalException(e);
        }
    }

    /**
     * 判断token是否过期且合法
     *
     * @return boolean
     */
    public static boolean validateToken() {
        String token = getTokenString();
        if (StrUtil.isNotEmpty(token)) {
            try{
                parseToken(token);
                return true;
            } catch (Exception e){
                return false;
            }
        } else {
            log.info("请求中无token认证信息");
            return false;
        }
    }

    /**
     * 过期时间次日凌晨2点
     *
     * @return expire
     */
    public static long getExpire() {
        // return System.currentTimeMillis()+3000;
        // 说明: https://blog.csdn.net/u014044812/article/details/79231738
        return LocalDate.now().plusDays(1).atTime(2, 0, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获取用户id 此系统以用户id做subject
     *
     * @return subject
     */
    public static LoginUser getCallerInfo() {
        String token = getTokenString();
        if (StrUtil.isNotEmpty(token)) {
            Claims claim = parseToken(token);
            return JSON.parseObject(claim.getSubject(), LoginUser.class);
        } else {
            log.info("请求中无token认证信息");
            return null;
        }
    }

    private static String getTokenString() {
        return WebUtil.getRequestParameter(CommonConstant.TOKEN);
    }


    /**
     * 获取用户id
     *
     * @return userId
     */
    public static Integer getUserId() {
        LoginUser user = getCallerInfo();
        return (null == user) ? -1 : user.getUserId();
    }

    /**
     * 获取用户账号
     *
     * @return userAccount
     */
    public static String getUserAccount() {
        LoginUser user = getCallerInfo();
        return (null == user) ? StringPool.EMPTY : user.getAccount();
    }

    /**
     * 获取用户名
     *
     * @return userName
     */
    public static String getUserName() {
        LoginUser user = getCallerInfo();
        return (null == user) ? StringPool.EMPTY : user.getUserName();
    }

    /**
     * 获取用户角色
     *
     * @return userName
     */
    public static String getUserRole() {
        LoginUser user = getCallerInfo();
        return (null == user) ? StringPool.EMPTY : user.getRoleName();
    }

    /**
     * 获取租户ID
     *
     * @return tenantId
     */
    public static String getTenantId() {
        LoginUser user = getCallerInfo();
        return (null == user) ? StringPool.EMPTY : user.getTenantId();
    }
}
