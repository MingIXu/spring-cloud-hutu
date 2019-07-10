package com.hutu.common.core.enums;

/**
 * 自定义异常信息枚举
 *
 * @author hutu
 * @date 2018/6/26 16:27
 */
public enum ErrorMsgEnum {

    AUTH_FAIL(20002, "鉴权失败"),
    THREAD_POOL_OVERFLOW(20001, "线程池满载"),

    USER_NO_PERMISSION(10008, "用户无任何权限信息"),
    USER_NO_ROLE(10007, "用户无任何角色信息"),
    TOKEN_IS_EXPIRE(10006, "token 过期"),
    NOT_FOUNT_TOKEN(10005, "not found token"),
    NULL_POINTER_EXCEPTION(10004, "空指针异常"),
    TOKEN_IS_INVALID(10003, "token无效"),
    PASSWORD_ERROR(10002, "密码错误"),
    USERNAME_NOT_EXIST(10001, "用户名不存在"),

    INTERNAL_SERVER_ERROR(500, "后台系统错误"),
    NOT_FOUND(404, "找不到请求资源"),
    SERVER_BUSY(402, "系统繁忙"),
    UNAUTHORIZED(401, "未授权");


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
