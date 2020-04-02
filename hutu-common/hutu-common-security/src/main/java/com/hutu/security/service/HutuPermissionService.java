package com.hutu.security.service;

import java.util.List;

/**
 * 获取用户权限接口
 *
 * @author hutu
 * @date 2019/7/10 16:37
 */
public interface HutuPermissionService {
    /**
     * 获取用户权限集合
     */
   default List<String> getUserPermissions(){
       return null;
   }
}
