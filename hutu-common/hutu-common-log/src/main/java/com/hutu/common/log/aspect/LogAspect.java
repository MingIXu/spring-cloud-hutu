package com.hutu.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.hutu.common.log.SpringContextHolder;
import com.hutu.common.log.annotation.OperationLog;
import com.hutu.common.log.event.SysLogEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 * @author hutu
 * @date 2018/6/10 15:12
 */
@Aspect
public class LogAspect {
	private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

	private final static String UNKNOWN = "unKnown";
	private final static String USER_AGENT = "user-agent";

    @Around("@annotation(operationLog)")
	public Object around(ProceedingJoinPoint point, OperationLog operationLog) throws Throwable {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        logger.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);

        long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

        SpringContextHolder.publishEvent(new SysLogEvent(new Object()));
		//保存日志
		savelog(point, time, result);

		return result;
	}

	private void savelog(ProceedingJoinPoint joinPoint, long time, Object result) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
//
//		Log log = new Log();
//		OperationLog operationLog = method.getAnnotation(OperationLog.class);
//		if (operationLog != null) {
//			//注解上的描述
//			log.setDescription(operationLog.value());
//		}
//		//请求的方法名
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = signature.getName();
//		log.setMethod(className + "." + methodName + "()");
//		//请求的参数
//		Object[] args = joinPoint.getArgs();
//		//用户名
//		String username = UNKNOWN;
//		try {
//			username = JwtUtils.getCallerInfo().name;
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//		}
//		try {
//			log.setResult(JSON.toJSONString(result));
//			log.setParameter(JSON.toJSONString(args));
//			//获取request
//			HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//			//设置IP地址
//			log.setIp(HttpContextUtils.getIpAddrByRequest(request));
//			log.setUri(request.getRequestURI());
//			log.setUserAgent(request.getHeader(USER_AGENT));
//			log.setUsername(username);
//			log.setSpendTime((int) time);
//			log.setStartTime(new Date());
//			//保存系统日志
//			logService.save(log);
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//		}
	}
}
