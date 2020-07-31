
package com.hutu.utils.token;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hutu.core.constant.StringPool;
import com.hutu.core.exception.GlobalException;
import com.hutu.core.utils.JwtUtil;
import com.hutu.core.constant.CommonConstant;
import com.hutu.core.enums.ResultCode;
import com.hutu.utils.WebUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * token工具类
 *
 * @author hutu
 * @date 2019-12-13 9:29
 */
@Slf4j
public class TokenUtil {

    /**
     * 生成 jwt token
     */
    public static TokenInfo createToken(Object sourceToken) {
        long expire = getExpire();
        return new TokenInfo(JwtUtil.createToken(sourceToken, expire), (int) expire / 1000);
    }

    /**
     * 解析jwt token
     */
    public static Claims parseToken(String sourceToken) {
        return JwtUtil.parseToken(sourceToken);
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
            long expire = getExpire();
            return new TokenInfo(JwtUtil.refreshToken(token, expire), (int) expire / 1000);
        } catch (Exception e) {
            log.info("刷新Token失败");
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
            try {
                parseToken(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            log.info("请求中无token认证信息");
            return false;
        }
    }

    /**
     * 获取登录用户信息
     *
     * @return subject
     */
    public static LoginUser getCallerInfo() {
        String token = getTokenString();
        if (StrUtil.isNotEmpty(token)) {
            Claims claim = parseToken(token);
            return JSONUtil.toBean(claim.getSubject(), LoginUser.class);
        } else {
            log.info("请求中无token认证信息");
            return null;
        }
    }

    /**
     * 获取用户id
     *
     * @return userId
     */
    public static Long getUserId() {
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


    public static String getTokenString() {
        return WebUtil.getRequestParameter(CommonConstant.TOKEN);
    }

    /**
     * 过期时间次日凌晨2点
     * 说明: https://blog.csdn.net/u014044812/article/details/79231738
     *
     * @return expire
     */
    public static long getExpire() {
        return LocalDate.now().plusDays(1).atTime(2, 0, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
