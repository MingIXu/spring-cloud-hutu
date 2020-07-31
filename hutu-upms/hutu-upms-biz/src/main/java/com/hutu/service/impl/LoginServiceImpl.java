package com.hutu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hutu.core.R;
import com.hutu.core.enums.ResultCode;
import com.hutu.core.utils.TreeNode;
import com.hutu.core.utils.TreeUtil;
import com.hutu.entity.*;
import com.hutu.mapper.*;
import com.hutu.service.LoginService;
import com.hutu.service.RolePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author hutu.
 * @date 2017/11/6 10:38
 */
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {


    final private UserMapper userMapper;

    final private UserRoleMapper userRoleMapper;

    final private UserPermissionMapper usePermissionMapper;

    final private PermissionMapper permissionMapper;

    final private RolePermissionMapper rolePermissionMapper;

    RolePermissionService rolePermissionService;

    @Override
    public R login(String username, String password) {
        List<User> list = userMapper.selectList(new QueryWrapper<User>().eq("name", username));
        User user = (list != null && list.size() == 1) ? list.get(0) : null;
        if (user == null || !user.getPass().equals(password)) {
            return R.error(ResultCode.USERNAME_OR_PASS_ERROR);
        } else {
            return R.ok(user);
        }
    }
    @Override
    public List<Permission> getPermissionByUserId(Integer userId) {
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
            return new ArrayList<>();
        }
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>()
                .in("id",permissionIds).orderByAsc("sort"));
        return permissionList;
    }

    @Override
    public List getUserPermissions(Integer userId) {
        List<Permission> list = getPermissionByUserId(userId);
        List<String> permissions = list.stream().map(Permission::getPermissionValue).collect(Collectors.toList());
        return permissions;
    }

    @Override
    public List getPermissionTree(){
        List<Permission> list = permissionMapper.selectList(null);
        List<TreeNode> treeList = new ArrayList<>();
        for (Permission obj : list) {
            TreeNode treenode = new TreeNode(obj.getId(), obj.getParentId(), obj.getName());
            treeList.add(treenode);
        }
        return TreeUtil.buildByRecursive(treeList, 0);
    }

    @Override
    public List getRolePermissionIds(Integer roleId){
        // 找到是目录的菜单
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>().eq("type",1));
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("roleId",roleId));
        List<Integer> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        for (Permission obj : permissionList) {
            permissionIds.removeIf(id->obj.getId().equals(id));
        }
        return permissionIds;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRolePermission(Integer[] permissionIds, Integer roleId) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("roleId", roleId));

        Set<RolePermission> rolePermissions = new HashSet<>();
        List<Permission> permissionList = permissionMapper.selectList(null);

        for (int permissionId : permissionIds) {
            findParentPermission(permissionList, rolePermissions, roleId, permissionId);
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissions);

        return true;
    }
    public void findParentPermission(List<Permission> permissionList,Set<RolePermission> rolePermissions,int roleId,int permissionId){
        int parentId = 0;
        for (Permission obj : permissionList) {
            if (obj.getId().equals(permissionId)&&obj.getParentId()!=0){
                for (Permission tmp : permissionList) {
                    if (tmp.getId().equals(obj.getParentId())){
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(roleId);
                        rolePermission.setPermissionId(tmp.getId());
                        rolePermissions.add(rolePermission);
                        break;
                    }
                }
                parentId = obj.getParentId();
                break;
            }
        }
        if (parentId!=0){
            findParentPermission(permissionList,rolePermissions,roleId,parentId);
        }
    }
}
