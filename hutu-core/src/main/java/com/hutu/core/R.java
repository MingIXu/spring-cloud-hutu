package com.hutu.core;

import com.hutu.core.enums.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 统一前端返回对象
 *
 * @author hutu
 * @date 2018/7/19 17:00
 */
@Setter
@Getter
public class R<M> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private M body;

    public R() {
        this(ResultCode.OK.code, ResultCode.OK.msg);
    }

    public R(M body) {
        this(ResultCode.OK.code, ResultCode.OK.msg, body);
    }

    public R(String msg, M body) {
        this(ResultCode.OK.code, msg, body);
    }

    public R(int code, String msg) {
        this(code,msg, null);
    }

    public R(int code, String msg, M body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public static R error() {
        return error(ResultCode.INTERNAL_SERVER_ERROR);
    }

    public static R error(String msg) {
        return new R(ResultCode.INTERNAL_SERVER_ERROR.code, msg);
    }

    public static R error(int code, String msg) {
        return new R(code, msg);
    }

    public static R error(ResultCode resultCode) {
        return new R(resultCode.code, resultCode.msg);
    }

    public static R ok() {
        return new R(ResultCode.OK.code, ResultCode.OK.msg);
    }

    public static R ok(int code, String msg) {
        return new R(code, msg);
    }

    public static R ok(String msg) {
        return new R(ResultCode.OK.code, msg);
    }

    public static R ok(ResultCode resultCode) {
        return new R(resultCode.code, resultCode.msg);
    }

    public static <M> R<M> ok(M body) {
        return new R<>(body);
    }

    public static <M> R<M> ok(String msg, M body) {
        return new R<>(msg,body);
    }
}
