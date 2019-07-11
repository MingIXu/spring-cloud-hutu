package com.hutu.common.core.entity;

import com.hutu.common.core.enums.ErrorMsgEnum;
import com.hutu.common.core.enums.InfoMsgEnum;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 统一前端返回对象
 *
 * @author hutu
 * @date 2018/7/19 17:00
 */
public class R implements Serializable {

    private static final long serialVersionUID = 1L;
    public Integer code;
    public String msg;
    public HashMap<String,Object> data;

    public R() {
        this.code = InfoMsgEnum.OK.code;
        this.msg = InfoMsgEnum.OK.desc;
        this.data = new HashMap<>();
    }

    public static R error() {
        return error(ErrorMsgEnum.INTERNAL_SERVER_ERROR);
    }

    public static R error(ErrorMsgEnum eme) {
        R r = new R();
        r.code = eme.code;
        r.msg = eme.desc;
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.code = ErrorMsgEnum.INTERNAL_SERVER_ERROR.code;
        r.msg = msg;
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.code = code;
        r.msg = msg;
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(InfoMsgEnum isg) {
        R r = new R();
        r.code = isg.code;
        r.msg = isg.desc;
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.msg = msg;
        return r;
    }

    public R put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
