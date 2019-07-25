package com.hutu.common.core.enums;

/**
 * 自定义异常信息枚举
 *
 * @author hutu
 * @date 2018/6/26 16:27
 */
public enum ErrorMsgEnum {
    /**
     *
     */
    AUTH_FAIL(1200, "鉴权失败"),

    TOKEN_NEED_REFRESH(1103, "请刷新 token"),
    TOKEN_IS_EXPIRE(1102, "token 过期"),
    NOT_FOUNT_TOKEN(1101, "请求中无 token 信息"),
    TOKEN_IS_INVALID(1100, "token 无效"),

    USER_NO_PERMISSION(1004, "用户无任何权限信息"),
    USER_NO_ROLE(1003, "用户无任何角色信息"),
    PASSWORD_ERROR(1002, "密码错误"),
    USERNAME_NOT_EXIST(1001, "用户名不存在"),
    USERNAME_OR_PASS_ERROR(1000,"用户名或密码错误"),

    NOT_FOUND(404, "找不到请求资源"),
    SERVER_BUSY(402, "系统繁忙"),
    UNAUTHORIZED(401, "未授权"),

    THREAD_POOL_OVERFLOW(511, "线程池满载"),
    NULL_POINTER_EXCEPTION(510, "空指针异常"),
    INTERNAL_SERVER_ERROR(500, "后台系统错误");


    public int code;
    public String desc;

    ErrorMsgEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.code + " " + this.desc;
    }
}
