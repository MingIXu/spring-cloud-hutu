package com.hutu.service;

import com.hutu.annotation.Logical;

/**
 * 鉴权逻辑顶级接口类
 *
 * @author hutu
 * @date 2019/7/10 16:37
 */
public interface SecurityService {

    /**
     * 鉴权逻辑
     *
     * @param logical 逻辑
     * @param role    包含角色
     * @return boolean
     */
    boolean doAuth(Logical logical, String[] role);
}
