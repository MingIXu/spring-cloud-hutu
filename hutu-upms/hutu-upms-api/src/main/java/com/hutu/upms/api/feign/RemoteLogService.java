package com.hutu.upms.api.feign;

import com.hutu.common.core.constant.ServiceNameConstant;
import com.hutu.common.core.entity.R;
import com.hutu.upms.api.dto.Log;
import com.hutu.upms.api.feign.factory.LoginServiceFallbackFactory;
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

}
