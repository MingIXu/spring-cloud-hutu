package com.hutu.common.enums;

/**
 * 自定义通知信息枚举
 * @author hutu
 * @date 2018/6/26 16:27
 */
public enum InfoMsgEnum {

    OK(200,"成功");
    public int code;
    public String desc;
    InfoMsgEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
