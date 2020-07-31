package com.hutu.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证方式类型
 *
 * @author hutu
 * @date 2020/6/8 3:14 下午
 */
@Getter
@AllArgsConstructor
public enum AuthType {
    /**
     * 账号密码
     */
    PASSWORD("password"),
    /**
     * refreshToken方式
     */
    REFRESH_TOKEN("refreshToken"),
    /**
     * 验证码+账号密码
     */
    CAPTCHA("captcha");

    public String value;
}
