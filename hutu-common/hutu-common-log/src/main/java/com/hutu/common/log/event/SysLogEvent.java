package com.hutu.common.log.event;

import com.hutu.upms.api.entity.Log;
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
