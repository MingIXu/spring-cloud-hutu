package com.hutu.service;

import com.hutu.cloud.core.R;
import com.hutu.cloud.core.constant.AppNamesConstant;
import com.hutu.entity.SysApiLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 保存操作日志接口
 *
 * @author hutu
 * @date 2020/5/19 5:36 下午
 */
@FeignClient(value = AppNamesConstant.HUTU_UPMS)
public interface ApiLogService {

    @PostMapping("saveApiLog")
    R save(SysApiLog log);

}
