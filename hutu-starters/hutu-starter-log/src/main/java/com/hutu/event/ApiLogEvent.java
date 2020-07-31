package com.hutu.event;

import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 *
 * @author hutu
 */
public class ApiLogEvent extends ApplicationEvent {
    public ApiLogEvent(Object source) {
        super(source);
    }
}
