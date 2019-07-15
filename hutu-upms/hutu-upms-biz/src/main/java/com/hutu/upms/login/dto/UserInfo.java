package com.hutu.upms.login.dto;

import com.hutu.common.core.entity.CallerInfo;

/**
 * 用户信息
 *
 * @author hutu
 * @date 2019/7/15 15:27
 */
public class UserInfo extends CallerInfo {

    private String avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
    private String[] roles = {"admin"};
    private String orgName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
