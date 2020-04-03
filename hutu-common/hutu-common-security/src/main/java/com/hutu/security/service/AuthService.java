package com.hutu.security.service;

import com.hutu.security.annotation.Logical;

/**
 * 鉴权逻辑
 *
 * @author hutu
 * @date 2019/7/10 16:37
 */
public interface AuthService {

    /**
     * 鉴权逻辑
     *
     * @param logical 逻辑
     * @param role    包含角色
     * @return boolean
     */
    boolean doAuth(Logical logical, String[] role);
}
