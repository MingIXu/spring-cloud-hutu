package com.hutu.admin.dto;

import com.hutu.api.entity.Permission;
import com.hutu.common.utils.TreeNode;

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
