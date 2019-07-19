package com.hutu.common.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;


/**
 * @author
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {
	private final Object remoteLogService;

//	@Async("自定义线程池")
	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
//		SysLog sysLog = (SysLog) event.getSource();
//		remoteLogService.saveLog(sysLog, SecurityConstants.FROM_IN);
	}
}
