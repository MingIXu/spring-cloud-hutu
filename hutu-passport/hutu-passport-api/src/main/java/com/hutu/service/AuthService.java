package com.hutu.service;

import com.hutu.core.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 鉴权中心对外暴露接口
 *
 * @author hutu
 * @date 2020/6/5 3:59 下午
 */
@FeignClient(value = ServiceNameConstant.PASSPORT)
public interface AuthService {

    /**
     * 判断是否有权限
     * @param permissionCode 资源代码
     * @param logical 鉴权逻辑
     * @return boolean
     */
    @GetMapping("/hasPermission")
    boolean hasPermission(@RequestParam("permissionCode") String permissionCode, @RequestParam("logical") int logical);
}
