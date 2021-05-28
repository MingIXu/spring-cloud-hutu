package com.hutu.cloud.core.exception;

import com.hutu.cloud.core.enums.CommonStatusEnum;
import com.hutu.cloud.core.enums.IResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author hutu
 * @date 2018/3/28 10:15
 */
@Setter
@Getter
public class GlobalException extends RuntimeException {

	private int code;

	public GlobalException() {
		this(CommonStatusEnum.INTERNAL_SERVER_ERROR);
	}

	public GlobalException(Throwable e) {
		this(CommonStatusEnum.INTERNAL_SERVER_ERROR, e);
	}

	public GlobalException(String msg) {
		this(CommonStatusEnum.INTERNAL_SERVER_ERROR.getCode(), msg, null);
	}

	public GlobalException(String msg, Throwable e) {
		this(CommonStatusEnum.INTERNAL_SERVER_ERROR.getCode(), msg, e);
	}

	public GlobalException(IResultCode resultCode) {
		this(resultCode.getCode(), resultCode.getMsg(), null);
	}

	public GlobalException(IResultCode resultCode, Throwable e) {
		this(resultCode.getCode(), resultCode.getMsg(), e);
	}

	public GlobalException(int code, String msg, Throwable e) {
		super(msg, e);
		this.code = code;
	}

}
