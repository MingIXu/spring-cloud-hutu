package com.hutu.auth.handler;

import com.hutu.auth.entity.R;
import com.hutu.common.security.utils.HttpContextUtils;
import com.hutu.upms.api.feign.RemoteLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权
 *
 * @author hutu
 * @date 2019/6/20 15:17
 */
@RestController
public class AuthHandler{
    @Autowired
    RemoteLoginService remoteLoginService;

    @RequestMapping("login")
    public R login(String username, String password) {
        return remoteLoginService.login(username, password);
    }
}
