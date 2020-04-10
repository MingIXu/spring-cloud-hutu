package com.hutu.log.event;

import com.alibaba.fastjson.JSON;
import com.hutu.api.entity.Log;
import com.hutu.common.utils.SpringUtil;
import com.hutu.common.utils.WebUtil;
import com.hutu.log.annotation.ApiLog;

import java.util.Date;

/**
 * 日志信息事件发送
 *
 * @author hutu
 * @date 2019-12-07 20:21
 */
public class ApiLogPublisher {
    public static void publishEvent(String methodName, String className, ApiLog apiLog, long startTime, long time, Object[] args, Object result) {
        Log logApi = new Log()
                .setType(apiLog.type().value)
                .setDescription(apiLog.value())
                .setStartTime(new Date(startTime))
                .setSpendTime(time)
                .setClassName(className)
                .setMethod(methodName)
                .setParameter(JSON.toJSONString(args))
                .setResult(JSON.toJSONString(result))
                .setIp(WebUtil.getIP())
                .setUri(WebUtil.getRequestUri())
                .setUrl(WebUtil.getRequest().getRequestURL().toString())
                .setUserAgent(WebUtil.getUserAgent());
        SpringUtil.publishEvent(new ApiLogEvent(logApi));
    }

}
