package com.hutu.common.security.service;

import java.util.List;

public interface HutuPermissionService {
    /**
     * 获取用户权限集合
     */
    List<String> getUserPermissions(Integer userId);
}
