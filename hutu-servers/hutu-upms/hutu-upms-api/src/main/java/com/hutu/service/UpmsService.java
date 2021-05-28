package com.hutu.service;

import com.hutu.cloud.core.R;
import com.hutu.cloud.core.constant.AppNamesConstant;
import com.hutu.entity.User;
import com.hutu.fallback.UpmsServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * rest接口测试
 *
 * @author hutu
 * @date 2020/5/19 5:36 下午
 */
@FeignClient(value = AppNamesConstant.HUTU_UPMS, fallback = UpmsServiceFallback.class)
public interface UpmsService {

    @GetMapping("user/{id}")
    R<User> user(@PathVariable("id") Long id);

}
