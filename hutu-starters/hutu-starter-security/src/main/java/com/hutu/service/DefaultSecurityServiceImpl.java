package com.hutu.service;

import cn.hutool.core.util.ArrayUtil;
import com.hutu.annotation.Logical;
import com.hutu.core.constant.StringPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * 默认鉴权实现
 *
 * @author hutu
 * @date 2019-12-06 18:46
 */
@ConditionalOnClass(AuthService.class)
public class DefaultSecurityServiceImpl implements SecurityService {

    @Autowired
    AuthService authService;

    @Override
    public boolean doAuth(Logical logical, String[] permissionCodes) {
        // 空值则不需要校验
        if (permissionCodes == null || permissionCodes.length == 0) {
            return true;
        }

        return authService.hasPermission(ArrayUtil.join(permissionCodes, StringPool.COMMA), Logical.OR.value);
    }
}
