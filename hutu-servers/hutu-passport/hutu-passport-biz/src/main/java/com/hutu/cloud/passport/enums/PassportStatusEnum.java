package com.hutu.cloud.passport.enums;

import com.hutu.cloud.core.enums.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @desc PassportErrorCodeEnum
 *
 * @author hutu
 * @date 2021/3/31 1:56 下午
 */
@Getter
@AllArgsConstructor
public enum PassportStatusEnum implements IResultCode {

	/**
	 * 刷新令牌不存在可能已过期请重新登录
	 */
	REFRESH_TOKEN_NOT_FOUND(2005, "刷新令牌不存在可能已过期请重新登录"),
	/**
	 * 手机号不存在
	 */
	PHONE_NUM_NOT_FOUND(2004, "手机号不存在"),
	/**
	 * 验证码已过期
	 */
	CAPTCHA_IS_EMPTY(2003, "验证码已过期"),
	/**
	 * 密码不能为空
	 */
	PASSWORD_IS_EMPTY(2002, "密码不能为空"),
	/**
	 * 用户名不存在
	 */
	USERNAME_NOT_EXIST(2001, "用户名不存在"),
	/**
	 * 用户名或密码错误
	 */
	USERNAME_OR_PASS_ERROR(2000, "用户名或密码错误");

	public int code;

	public String msg;

}
