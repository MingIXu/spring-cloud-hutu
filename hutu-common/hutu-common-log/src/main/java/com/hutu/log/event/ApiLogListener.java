package com.hutu.log.event;

import com.hutu.api.RemoteLogService;
import com.hutu.api.entity.Log;
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

    private final RemoteLogService remoteLogService;

    @Async
    @Order
    @EventListener(ApiLogEvent.class)
    public void saveSysLog(ApiLogEvent event) {
        Log logApi = (Log) event.getSource();
        remoteLogService.create(logApi);
    }
}
