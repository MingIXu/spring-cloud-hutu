//package com.hutu.common.security.aspect;
//
//import com.alibaba.fastjson.JSON;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.lang.reflect.Method;
//import java.util.Date;
//
///**
// * 系统日志，切面处理类
// * @author hutu
// * @date 2018/6/10 15:12
// */
//@Aspect
//@Component
//public class LogAspect {
//	final static Logger logger = LoggerFactory.getLogger(LogAspect.class);
//	private final static String UNKNOWN = "unKnown";
//	private final static String USER_AGENT = "user-agent";
//
//	@Autowired
//	private LogService logService;
//
//	@Pointcut("@annotation(com.hutu.common.annotation.OperationLog)")
//	public void logPointCut() {
//
//	}
//
//	@Around("logPointCut()")
//	public Object around(ProceedingJoinPoint point) throws Throwable {
//		long beginTime = System.currentTimeMillis();
//		//执行方法
//		Object result = point.proceed();
//		//执行时长(毫秒)
//		long time = System.currentTimeMillis() - beginTime;
//
//		//保存日志
//		savelog(point, time, result);
//
//		return result;
//	}
//
//	private void savelog(ProceedingJoinPoint joinPoint, long time, Object result) {
//		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//		Method method = signature.getMethod();
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
//	}
//}
