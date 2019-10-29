package com.hutu.upms.login.controller;

import com.hutu.common.core.entity.CallerInfo;
import com.hutu.common.core.entity.R;

import com.hutu.common.boot.utils.TokenUtils;
import com.hutu.upms.login.dto.UserInfo;
import com.hutu.upms.login.service.LoginService;
import com.hutu.upms.api.entity.Organization;
import com.hutu.upms.admin.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录逻辑
 *
 * @author hutu
 * @date 2018/8/16 9:33
 */
@Api(tags = "登录控制器")
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    OrganizationService organizationService;
    @ApiOperation("登录接口")
    @GetMapping("login")
    public R login(String username, String password) {
        return loginService.login(username, password);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("getUserInfo")
    public R getUserInfo() {
        Organization organization = organizationService.getById(TokenUtils.getTenantId());
        UserInfo userInfo = new UserInfo();
        CallerInfo callerInfo = TokenUtils.getCallerInfo();
        userInfo.tenantId = callerInfo.tenantId;
        userInfo.uid = callerInfo.uid;
        userInfo.name = callerInfo.name;
        userInfo.nick = callerInfo.nick;
        userInfo.setOrgName(organization==null?"unknown":organization.getName());
        return R.ok().put("user", userInfo);
    }

    @ApiOperation("获取用户权限信息")
    @GetMapping("getPermissionByUserId")
    public R getPermissionByUserId() {
        return R.ok().put("list",loginService.getPermissionByUserId(TokenUtils.getUserId()));
    }

    @ApiOperation("获取角色权限树")
    @GetMapping("getRolePermissionTree")
    public R getRolePermissionTree(Integer roleId) {
        return R.ok().put("treeData",loginService.getPermissionTree()).put("keys",loginService.getRolePermissionIds(roleId));
    }

    @ApiOperation("更新角色权限树")
    @PostMapping("updateRolePermission")
    public R updateRolePermission(@RequestBody Integer[] permissionIds, Integer roleId) {
        return R.ok().put("list",loginService.updateRolePermission(permissionIds,roleId));
    }

    @ApiOperation("退出登录")
    @GetMapping("logout")
    public R logout() {
        System.out.println("456");
        return R.ok();
    }
}
