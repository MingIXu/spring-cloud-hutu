package com.hutu.event;

import cn.hutool.json.JSONUtil;
import com.hutu.annotation.ApiLog;
import com.hutu.entity.SysApiLog;
import com.hutu.utils.SpringUtil;
import com.hutu.utils.WebUtil;

import static com.hutu.core.utils.DateUtils.getDateTimeOfTimestamp;

/**
 * 日志信息事件发送
 *
 * @author hutu
 */
public class ApiLogPublisher {
    public static void publishEvent(String methodName, String className, ApiLog apiLog, long startTime, long time, Object[] args, Object result) {
        SysApiLog logApi = new SysApiLog()
                .setType(apiLog.type().value)
                .setDescription(apiLog.value())
                .setStartTime(getDateTimeOfTimestamp(startTime))
                .setSpendTime(time)
                .setClassName(className)
                .setMethod(methodName)
                .setParameter(JSONUtil.toJsonStr(args))
                .setResult(JSONUtil.toJsonStr(result))
                .setIp(WebUtil.getIP())
                .setUri(WebUtil.getRequestUri())
                .setUserAgent(WebUtil.getUserAgent());
        SpringUtil.publishEvent(new ApiLogEvent(logApi));
    }


}
