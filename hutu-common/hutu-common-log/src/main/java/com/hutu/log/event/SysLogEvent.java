package com.hutu.log.event;

import com.hutu.api.entity.Log;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lengleng
 * 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {
	private final Log sysLog;
}
