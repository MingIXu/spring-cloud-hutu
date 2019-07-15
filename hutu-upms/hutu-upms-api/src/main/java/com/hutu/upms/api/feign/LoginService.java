package com.hutu.upms.api.feign;

import com.hutu.common.core.constant.ServiceNameConstant;
import com.hutu.common.core.entity.R;
import com.hutu.upms.api.feign.factory.LoginServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lengleng
 * @date 2018/6/22
 */
@FeignClient(value = ServiceNameConstant.UPMS, fallbackFactory = LoginServiceFallbackFactory.class)
public interface LoginService {
    /**
     * 用户密码登录
     */
    @GetMapping("login")
    public R login(String username, String password);

}
