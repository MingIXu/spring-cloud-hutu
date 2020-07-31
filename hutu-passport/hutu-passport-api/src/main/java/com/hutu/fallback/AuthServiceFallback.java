package com.hutu.fallback;

import com.hutu.service.AuthService;

/**
 * AuthService 的降级逻辑
 *
 * @author hutu
 * @date 2020/6/17 5:31 下午
 */
public class AuthServiceFallback implements AuthService {
    @Override
    public boolean hasPermission(String permissionCode, int logical) {
        // 默认无权限
        return false;
    }
}
