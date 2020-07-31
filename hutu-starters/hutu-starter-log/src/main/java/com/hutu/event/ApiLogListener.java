package com.hutu.event;

import com.hutu.entity.SysApiLog;
import com.hutu.service.ApiLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步监听日志事件
 *
 * @author hutu
 */
@Slf4j
@AllArgsConstructor
public class ApiLogListener {

    private final ApiLogService remoteLogService;

    @Async
    @Order
    @EventListener(ApiLogEvent.class)
    public void saveSysLog(ApiLogEvent event) {
        SysApiLog logApi = (SysApiLog) event.getSource();
        remoteLogService.save(logApi);
    }
}
