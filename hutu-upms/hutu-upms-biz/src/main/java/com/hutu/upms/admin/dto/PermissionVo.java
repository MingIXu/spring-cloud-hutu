package com.hutu.upms.admin.dto;

import com.hutu.auth.entity.TreeNode;
import com.hutu.upms.admin.entity.Permission;

public class PermissionVo extends TreeNode {

    Permission permission;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public PermissionVo(int id, int parentId, String label, Permission permission) {
        super(id, parentId, label);
        this.permission = permission;
    }
}
