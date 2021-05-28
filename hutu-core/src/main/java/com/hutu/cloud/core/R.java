package com.hutu.cloud.core;

import com.hutu.cloud.core.enums.CommonStatusEnum;
import com.hutu.cloud.core.enums.IResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 统一前端返回对象
 *
 * @author hutu
 * @date 2018/7/19 17:00
 */
@Setter
@Getter
@ToString
public class R<T> implements Serializable {

	private static final long serialVersionUID = 6424969590590309869L;

	private int code;

	private String message;

	private T body;

	public R() {
	}

	private R(int code, String msg, T body) {
		this.code = code;
		this.message = msg;
		this.body = body;
	}

	public static <T> R<T> ok() {
		return ok(null);
	}

	public static <T> R<T> ok(T data) {
		return ok(data, CommonStatusEnum.OK.getMsg());
	}

	public static <T> R<T> ok(T data, String msg) {
		return data(data, CommonStatusEnum.OK.getCode(), msg);
	}

	public static <T> R<T> failed(String msg) {
		return failed(CommonStatusEnum.INTERNAL_SERVER_ERROR.getCode(), msg);
	}

	public static <T> R<T> failed(IResultCode rc) {
		return failed(rc.getCode(), rc.getMsg());
	}

	public static <T> R<T> failed(int code, String msg) {
		return data(null, code, msg);
	}

	private static <T> R<T> data(T body, int code, String msg) {
		return new R<>(code, msg, body);
	}

}
