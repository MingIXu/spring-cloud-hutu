package com.hutu.common.security.utils;

import com.alibaba.fastjson.JSON;
import com.hutu.auth.entity.CallerInfo;
import com.hutu.auth.enums.ErrorMsgEnum;
import com.hutu.auth.exception.GlobalException;
import com.hutu.auth.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jwt 工具类
 *
 * @author hutu
 * @date 2019/7/10 13:44
 */
public class TokenUtils extends JwtUtils {

    private final static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 获取用户id 此系统以用户id做subject
     *
     * @return subject
     */
    public static CallerInfo getCallerInfo(String token) {
        if (StringUtils.isNotEmpty(token)) {
            Claims claim = parseToken(token);
            if (claim != null) {
                return JSON.parseObject(claim.getSubject(), CallerInfo.class);
            }
            throw new GlobalException(ErrorMsgEnum.INTERNAL_SERVER_ERROR);
        } else {
            throw new GlobalException(ErrorMsgEnum.NOT_FOUNT_TOKEN);
        }
    }

    public static CallerInfo getCallerInfo() {
        return getCallerInfo(HttpContextUtils.getRequestToken());
    }


    public static Integer getUserId() {

        try {
            return getCallerInfo().uid;
        } catch (Exception e) {
            logger.info("getUserId 失败");
            return 0;
        }
    }

    public static String getUserName() {
        try {
            return getCallerInfo().nick;
        } catch (Exception e) {
            logger.info("getUserName 失败");
            return "unknown";
        }
    }
    public static Integer getTenantId() {
        try {
            return getCallerInfo().tenantId;
        } catch (Exception e) {
            logger.error("getTenantId 失败");
            throw e;
        }
    }
}
