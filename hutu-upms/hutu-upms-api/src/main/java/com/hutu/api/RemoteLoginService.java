package com.hutu.api;

import com.hutu.api.factory.LoginServiceFallbackFactory;
import com.hutu.common.core.constant.ServiceNameConstant;
import com.hutu.common.core.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 * @date 2018/6/22
 */
@FeignClient(value = ServiceNameConstant.UPMS, fallbackFactory = LoginServiceFallbackFactory.class)
public interface RemoteLoginService {

    /**
     * 用户密码登录
     * @param username
     * @param password
     * @return
     */
    @GetMapping("login")
    R login(@RequestParam("username") String username,@RequestParam("password") String password);

}
