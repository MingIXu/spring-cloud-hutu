package com.hutu.common.entity;

import com.hutu.common.enums.ResultCode;
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
    private M data;

    public R() {
        this(ResultCode.OK.code, ResultCode.OK.msg);
    }

    public R(M data) {
        this(ResultCode.OK.code, ResultCode.OK.msg, data);
    }

    public R(String msg, M data) {
        this(ResultCode.OK.code, msg, data);
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(int code, String msg, M data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public static <M> R<M> ok(M data) {
        return new R<>(data);
    }

    public static <M> R<M> ok(String msg, M data) {
        return new R<>(msg,data);
    }
}
