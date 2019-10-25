package com.hutu.common.log.aspect;

import com.hutu.common.log.annotation.OperationLog;
import com.hutu.common.log.event.SysLogEvent;
import com.hutu.upms.api.dto.Log;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Date;

/**
 * 系统日志，切面处理类
 * @author hutu
 * @date 2018/6/10 15:12
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class SysLogAspect {
	private final ApplicationEventPublisher publisher;

	@SneakyThrows
	@Around("@annotation(sysLog)")
	public Object around(ProceedingJoinPoint point, OperationLog sysLog) {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Object obj = point.proceed();
		Long endTime = System.currentTimeMillis();
		Long spendTime = endTime - startTime;
		Log log = new Log();
		log.setStartTime(new Date()).setMethod(strMethodName).setSpendTime(spendTime);
		publisher.publishEvent(log);
		return obj;
	}

}
