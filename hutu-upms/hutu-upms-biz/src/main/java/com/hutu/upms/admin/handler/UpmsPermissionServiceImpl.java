package com.hutu.upms.admin.handler;

import com.hutu.common.security.service.HutuPermissionService;
import com.hutu.upms.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户在本系统能所有权限权限
 *
 * @author hutu
 * @date 2019/7/11 14:34
 */
@Service
public class UpmsPermissionServiceImpl implements HutuPermissionService {

    @Autowired
    private LoginService loginService;

    @Override
    public List<String> getUserPermissions(Integer userId) {
        return loginService.getUserPermissions(userId);
    }
}
