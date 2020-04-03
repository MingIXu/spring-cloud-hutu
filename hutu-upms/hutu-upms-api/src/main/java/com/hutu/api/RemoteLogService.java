package com.hutu.api;

import com.hutu.common.constant.ServiceNameConstant;
import com.hutu.common.entity.R;
import com.hutu.api.entity.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date 2018/6/22
 */
@FeignClient(contextId = "RemoteLogService", value = ServiceNameConstant.UPMS)
public interface RemoteLogService {

    /**
     * 记录日志
     * @return
     */
    @PostMapping("log/create")
    R create(@RequestBody Log log);

    /**
     * 记录日志
     * @return
     */
    @GetMapping("log/read/{id}")
    R read(@PathVariable("id") String id);
}
