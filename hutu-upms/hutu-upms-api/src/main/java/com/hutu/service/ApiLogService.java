package com.hutu.service;

import com.hutu.core.R;
import com.hutu.core.constant.ServiceNameConstant;
import com.hutu.entity.SysApiLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 保存操作日志接口
 *
 * @author hutu
 * @date 2020/5/19 5:36 下午
 */
@FeignClient(value = ServiceNameConstant.UPMS)
public interface ApiLogService {

    @PostMapping("saveApiLog")
    R save(SysApiLog log);

}
