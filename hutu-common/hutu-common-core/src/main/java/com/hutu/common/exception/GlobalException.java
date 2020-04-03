package com.hutu.common.exception;


import com.hutu.common.enums.ResultCode;

/**
 * 自定义异常
 *
 * @author hutu
 * @date 2018/3/28 10:15
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;

    public GlobalException() {
        super(ResultCode.INTERNAL_SERVER_ERROR.msg);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.code;
    }

    public GlobalException(Throwable e) {
        super(e);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.code;
    }

    public GlobalException(String msg) {
        super(msg);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.code;
    }

    public GlobalException(String msg, Throwable e) {
        super(msg, e);
        this.code = ResultCode.INTERNAL_SERVER_ERROR.code;
    }

    public GlobalException(ResultCode eme) {
        super(eme.msg);
        this.code = eme.code;
    }

    public GlobalException(ResultCode eme, Throwable e) {
        super(eme.msg, e);
        this.code = eme.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
