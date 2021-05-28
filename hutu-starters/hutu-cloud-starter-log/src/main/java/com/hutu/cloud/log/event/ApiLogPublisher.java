package com.hutu.cloud.log.event;

import com.hutu.cloud.log.annotation.ApiLog;
import com.hutu.cloud.log.config.SysApiLog;
import com.hutu.cloud.common.util.SpringUtils;

/**
 * 日志信息事件发送
 *
 * @author hutu
 */
public class ApiLogPublisher {

	public static void publishEvent(String methodName, String className, ApiLog apiLog, long startTime, long time,
			Object[] args, Object result) {
		SysApiLog logApi = new SysApiLog();
		// .setType(apiLog.type().value)
		// .setDescription(apiLog.value())
		// .setStartTime(getDateTimeOfTimestamp(startTime))
		// .setSpendTime(time)
		// .setClassName(className)
		// .setMethod(methodName)
		// .setParameter(JSONUtil.toJsonStr(args))
		// .setResult(JSONUtil.toJsonStr(result))
		// .setIp(WebUtil.getIP())
		// .setUri(WebUtil.getRequestUri())
		// .setUserAgent(WebUtil.getUserAgent());
		SpringUtils.publishEvent(new ApiLogEvent(logApi));
	}

}
