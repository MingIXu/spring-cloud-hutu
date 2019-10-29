package com.hutu.common.log.event;

import com.hutu.upms.api.entity.Log;
import com.hutu.upms.api.feign.RemoteLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import sun.security.util.SecurityConstants;


/**
 * @author 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {
    private final RemoteLogService remoteLogService;

    //	@Async("自定义线程池")
    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        Log sysLog = event.getSysLog();
        log.info(sysLog.toString());
		remoteLogService.create(sysLog);
    }
}
