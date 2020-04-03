package com.hutu.log.event;

import com.hutu.log.entity.LogApi;
import com.hutu.log.service.ApiLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步监听日志事件
 *
 * @author hutu
 * @date 2019-12-07 20:21
 */
@Slf4j
@AllArgsConstructor
public class ApiLogListener {

    private final ApiLogService apiLogService;

    @Async
    @Order
    @EventListener(ApiLogEvent.class)
    public void saveSysLog(ApiLogEvent event) {
        LogApi logApi = (LogApi) event.getSource();
        apiLogService.saveLog(logApi);
    }
}
