package com.hutu.auth.handler;

import com.hutu.common.core.entity.R;
import com.hutu.upms.api.RemoteLogService;
import com.hutu.upms.api.RemoteLoginService;
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
    @Autowired
    RemoteLogService remoteLogService;
    @RequestMapping("login")
    public R login(String username, String password) {
        return remoteLoginService.login(username, password);
    }

    @RequestMapping("log")
    public R redLog(String id) {
        return remoteLogService.read(id);
    }
}
