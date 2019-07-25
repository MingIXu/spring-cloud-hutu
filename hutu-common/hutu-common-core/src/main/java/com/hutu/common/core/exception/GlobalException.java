package com.hutu.common.core.exception;

import com.hutu.common.core.enums.ErrorMsgEnum;

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
        super(ErrorMsgEnum.INTERNAL_SERVER_ERROR.desc);
        this.code = ErrorMsgEnum.INTERNAL_SERVER_ERROR.code;
    }

    public GlobalException(Throwable e) {
        super(e);
        this.code = ErrorMsgEnum.INTERNAL_SERVER_ERROR.code;
    }

    public GlobalException(String msg) {
        super(msg);
        this.code = ErrorMsgEnum.INTERNAL_SERVER_ERROR.code;
    }

    public GlobalException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorMsgEnum.INTERNAL_SERVER_ERROR.code;
    }

    public GlobalException(ErrorMsgEnum eme) {
        super(eme.desc);
        this.code = eme.code;
    }

    public GlobalException(ErrorMsgEnum eme, Throwable e) {
        super(eme.desc, e);
        this.code = eme.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
