package com.hutu.admin.handler;

import com.hutu.login.service.LoginService;
import com.hutu.security.annotation.Logical;
import com.hutu.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户在本系统能所有权限权限
 *
 * @author hutu
 * @date 2019/7/11 14:34
 */
@Service
public class UpmsPermissionServiceImpl implements AuthService {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean doAuth(Logical logical, String[] role) {
        return false;
    }
}
