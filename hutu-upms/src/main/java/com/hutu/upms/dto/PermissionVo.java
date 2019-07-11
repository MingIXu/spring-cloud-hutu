package com.hutu.upms.dto;

import com.hutu.common.core.entity.TreeNode;
import com.hutu.upms.entity.Permission;

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
