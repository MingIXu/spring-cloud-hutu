package com.hutu.upms.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hutu.common.security.service.HutuPermissionService;
import com.hutu.upms.entity.Permission;
import com.hutu.upms.entity.RolePermission;
import com.hutu.upms.entity.UserPermission;
import com.hutu.upms.entity.UserRole;
import com.hutu.upms.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户在本系统能所有权限权限
 *
 * @author hutu
 * @date 2019/7/11 14:34
 */
@Service
public class UpmsPermissionServiceImpl implements HutuPermissionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper usePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<String> getUserPermissions(Integer userId) {
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("userId",userId));
        List<UserPermission> userPermissionList = usePermissionMapper.selectList(new QueryWrapper<UserPermission>()
                .eq("userId",userId));
        Set<Integer> roleIds = userRoleList.stream().filter(obj -> obj.getRoleId() != 0).map(UserRole::getRoleId).collect(Collectors.toSet());
        Set<Integer> permissionIds = new HashSet<>();
        if (roleIds.size() > 0){
            List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>()
                    .in("roleId",roleIds));
            permissionIds = rolePermissionList.stream().filter(obj -> obj.getPermissionId() != 0).map(RolePermission::getPermissionId).collect(Collectors.toSet());
        }
        permissionIds.addAll(userPermissionList.stream().filter(obj -> obj.getPermissionId() != 0).map(UserPermission::getPermissionId).collect(Collectors.toSet()));
        // 无权限
        if (permissionIds.size()==0){
            return new ArrayList();
        }
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>()
                .in("id",permissionIds));
        return permissionList.stream().map(Permission::getPermissionValue).collect(Collectors.toList());
    }
}
