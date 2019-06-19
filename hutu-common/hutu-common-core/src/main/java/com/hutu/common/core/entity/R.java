package com.hutu.common.core.entity;

import com.hutu.common.core.enums.ErrorMsgEnum;
import com.hutu.common.core.enums.InfoMsgEnum;

import java.util.HashMap;

/**
 * 统一前端返回对象
 *
 * @author hutu
 * @date 2018/7/19 17:00
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    public static final String CODE = "code";
    public static final String MSG = "msg";

    public R() {
        put(CODE, InfoMsgEnum.OK.code);
        put(MSG, InfoMsgEnum.OK.desc);
    }

    public static R error() {
        return error(ErrorMsgEnum.INTERNAL_SERVER_ERROR);
    }

    public static R error(ErrorMsgEnum eme) {
        R r = new R();
        r.put(CODE, eme.code);
        r.put(MSG, eme.desc);
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.put(CODE, ErrorMsgEnum.INTERNAL_SERVER_ERROR.code);
        r.put(MSG, msg);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put(CODE, code);
        r.put(MSG, msg);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(InfoMsgEnum isg) {
        R r = new R();
        r.put(MSG, isg.desc);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put(MSG, msg);
        return r;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
