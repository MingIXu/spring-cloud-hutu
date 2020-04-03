package com.hutu.security.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.hutu.common.utils.StringPool;
import com.hutu.common.utils.token.TokenUtil;
import com.hutu.security.annotation.Logical;

import java.util.Arrays;

/**
 * 默认鉴权实现
 * @author hutu
 * @date 2019-12-06 18:46
 */
public class DefaultAuthServiceImpl implements AuthService {

    @Override
    public boolean doAuth(Logical logical, String[] role) {
        String userRole = TokenUtil.getUserRole();
        if (StrUtil.isBlank(userRole)) {
            return false;
        }
        String[] roles = StrUtil.split(userRole, StringPool.COMMA);
        if (logical.equals(Logical.OR)) {
            for (String r : role) {
                if (CollectionUtil.contains(Arrays.asList(roles), r)) {
                    return true;
                }
            }
            return false;
        } else {
            boolean flag = false;
            for (String r : role) {
                if (!CollectionUtil.contains(Arrays.asList(roles), r)) {
                    return false;
                }
                flag = true;
            }
            return flag;
        }
    }
}
