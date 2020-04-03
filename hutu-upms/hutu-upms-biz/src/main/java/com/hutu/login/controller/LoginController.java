package com.hutu.login.controller;

import com.hutu.admin.service.OrganizationService;
import com.hutu.api.entity.Organization;
import com.hutu.common.entity.R;
import com.hutu.common.utils.token.LoginUser;
import com.hutu.common.utils.token.TokenUtil;
import com.hutu.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
        return R.ok(new LoginUser());
    }

    @ApiOperation("获取用户权限信息")
    @GetMapping("getPermissionByUserId")
    public R getPermissionByUserId() {
        return R.ok(loginService.getPermissionByUserId(TokenUtil.getUserId()));
    }

    @ApiOperation("获取角色权限树")
    @GetMapping("getRolePermissionTree")
    public R getRolePermissionTree(Integer roleId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("treeData",loginService.getPermissionTree());
        map.put("keys",loginService.getRolePermissionIds(roleId));
        return R.ok(map);
    }

    @ApiOperation("更新角色权限树")
    @PostMapping("updateRolePermission")
    public R updateRolePermission(@RequestBody Integer[] permissionIds, Integer roleId) {
        return R.ok(loginService.updateRolePermission(permissionIds,roleId));
    }

    @ApiOperation("退出登录")
    @GetMapping("logout")
    public R logout() {
        System.out.println("456");
        return R.ok();
    }
}
