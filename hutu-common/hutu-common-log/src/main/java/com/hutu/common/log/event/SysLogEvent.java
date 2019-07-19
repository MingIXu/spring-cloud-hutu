package com.hutu.common.log.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author
 * 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(Object source) {
		super(source);
	}
}
