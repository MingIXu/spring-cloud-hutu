package com.hutu.cloud.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * 自定义异步异常处理
 *
 * @author hutu
 * @date 2021/3/25 2:23 下午
 */
@Slf4j
public class CustomAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
		// 自定义处理逻辑
		if (log.isErrorEnabled()) {
			log.error("Unexpected exception occurred invoking async method: " + method, throwable);
		}
	}

}