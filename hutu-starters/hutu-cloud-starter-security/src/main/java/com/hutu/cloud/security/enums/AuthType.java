package com.hutu.cloud.security.enums;

/**
 * @desc 鉴权类型
 *
 * @author hutu
 * @date 2021/3/4 4:01 下午
 */
public enum AuthType {

	/**
	 * 不校验权限
	 */
	NONE,
	/**
	 * 校验登录
	 */
	LOGIN,
	/**
	 * 校验请求客户端信息
	 */
	CLIENT,
	/**
	 * 校验登录和客户端信息，默认校验方式
	 */
	LOGIN_AND_CLIENT;

	/**
	 * 判断是否检查登录
	 */
	public boolean isCheckLogin() {
		return this.equals(AuthType.LOGIN) || this.equals(AuthType.LOGIN_AND_CLIENT);
	}

	/**
	 * 判断是否检查客户端信息
	 */
	public boolean isCheckClient() {
		return this.equals(AuthType.CLIENT) || this.equals(AuthType.LOGIN_AND_CLIENT);
	}

	/**
	 * 判断是否跳过所有检查
	 */
	public boolean isSkipAllCheck() {
		return this.equals(AuthType.NONE);
	}

}
