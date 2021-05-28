package com.hutu.cloud.log.aspect;

import com.hutu.cloud.log.annotation.ApiLog;
import com.hutu.cloud.log.event.ApiLogPublisher;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 系统日志，切面处理类
 *
 * @author hutu
 */
@Slf4j
@Aspect
public class ApiLogAspect {

	@SneakyThrows
	@Around("@annotation(apiLog)")
	public Object around(ProceedingJoinPoint point, ApiLog apiLog) {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		Long startTime = System.currentTimeMillis();
		Object result = null;
		try {
			result = point.proceed();
		}
		catch (Exception e) {
			result = e.getMessage();
			throw e;
		}
		finally {
			Long endTime = System.currentTimeMillis();
			long spendTime = endTime - startTime;
			// 记录日志
			ApiLogPublisher.publishEvent(strMethodName, strClassName, apiLog, startTime, spendTime, point.getArgs(),
					result);
		}
		return result;
	}

}
