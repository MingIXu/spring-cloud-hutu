package com.hutu.cloud.token.enums;

import com.hutu.cloud.core.enums.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 令牌状态码
 *
 * @author hutu
 * @date 2021/4/1 2:53 下午
 */
@Getter
@AllArgsConstructor
public enum TokenStatusEnum implements IResultCode {

	/**
	 * 后台系统没发现该令牌
	 */
	TOKEN_NOT_FOUNT_IN_SYSTEM(1102, "token not fount in system"),
	/**
	 * 令牌已过期
	 */
	TOKEN_EXPIRED(1101, "token expired"),
	/**
	 * 请求中无令牌
	 */
	TOKEN_NOT_FOUNT_IN_REQUEST(1100, "token not fount in request");

	public int code;

	public String msg;

}
