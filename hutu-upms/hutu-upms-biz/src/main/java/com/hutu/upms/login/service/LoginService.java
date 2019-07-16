package com.hutu.upms.login.service;



import com.hutu.auth.entity.R;

import java.util.List;

/**
 * @author hutu
 * @date 2017/11/3
 */
public interface LoginService {
    /**
     * 登录
     */
    R login(String username, String password);

    /**
     * 获取用户权限
     */
    List getPermissionByUserId(Integer userId);

    /**
     * 获取用户权限标识集合
     */
    List<String> getUserPermissions(Integer userId);

    /**
     * 获取权限树
     */
    List getPermissionTree();

    /**
     * 获取角色的权限集合
     */
    List getRolePermissionIds(Integer roleId);

    /**
     * 更新角色权限
     */
    boolean updateRolePermission(Integer[] permissionIds, Integer roleId);
}
