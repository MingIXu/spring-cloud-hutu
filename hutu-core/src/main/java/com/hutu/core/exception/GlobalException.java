package com.hutu.core.exception;


import com.hutu.core.enums.ResultCode;
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
        this(ResultCode.INTERNAL_SERVER_ERROR);
    }

    public GlobalException(Throwable e) {
        this(ResultCode.INTERNAL_SERVER_ERROR,e);
    }

    public GlobalException(String msg) {
        this(ResultCode.INTERNAL_SERVER_ERROR.code,msg,null);
    }

    public GlobalException(String msg, Throwable e) {
        this(ResultCode.INTERNAL_SERVER_ERROR.code,msg,e);
    }

    public GlobalException(ResultCode resultCode) {
        this(resultCode.code,resultCode.msg,null);
    }

    public GlobalException(ResultCode resultCode, Throwable e) {
        this(resultCode.code,resultCode.msg,e);
    }

    public GlobalException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }
}
