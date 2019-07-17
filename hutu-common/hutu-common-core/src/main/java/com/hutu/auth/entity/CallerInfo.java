package com.hutu.auth.entity;

import java.io.Serializable;

/**
 * 调用者信息
 *
 * @author hutu
 * @date 2018/8/8 16:49
 */
public class CallerInfo implements Serializable {

    /**
     * 用户ID
     */
    public Integer uid;
    /**
     * 用户名
     */
    public String name = "";
    /**
     * 昵称
     */
    public String nick = "";
    /**
     * 租户id
     */
    public Integer tenantId = null;
}