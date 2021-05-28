package com.hutu.cloud.core.enums;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常信息枚举
 *
 * @author hutu
 * @date 2018/6/26 16:27
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum implements IResultCode {

	/**
	 * 操作成功
	 */
	OK(HttpStatus.HTTP_OK, "成功"),
	/**
	 * 404 找不到请求资源
	 */
	NOT_FOUND(HttpStatus.HTTP_NOT_FOUND, "404 找不到请求资源"),
	/**
	 * 服务器异常
	 */
	INTERNAL_SERVER_ERROR(HttpStatus.HTTP_INTERNAL_ERROR, "服务器异常"),
	/**
	 * 请求超时
	 */
	HTTP_CLIENT_TIMEOUT(HttpStatus.HTTP_CLIENT_TIMEOUT, "请求超时"),
	/**
	 * 未授权
	 */
	UNAUTHORIZED(HttpStatus.HTTP_UNAUTHORIZED, "未授权"),
	/**
	 * 非法请求，外部请求
	 */
	NOT_ACCEPTABLE(HttpStatus.HTTP_NOT_ACCEPTABLE, "非法请求"),
	/**
	 * 服务限流
	 */
	SERVICE_IS_BUSY(429, "服务太忙啦,请稍后再试"),
	/**
	 * 请求头参数缺失
	 */
	MISSING_HEADER_PARAMETER(1003, "请求头参数缺失"),
	/**
	 * 验签失败
	 */
	VERIFY_SIGN_FAIL(1002, "验签失败"),
	/**
	 * 服务降级
	 */
	SERVICE_FALLBACK(1001, "服务降级中"),
	/**
	 * 线程池过载
	 */
	THREAD_POOL_OVERFLOW(1000, "线程池过载");

	public int code;

	public String msg;

}
