package com.hutu.cloud.config;

import com.hutu.cloud.error.CustomAsyncUncaughtExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 异步处理
 *
 * @author hutu
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableAsync
@EnableScheduling
public class CustomExecutorConfiguration extends AsyncConfigurerSupport {

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new CustomAsyncUncaughtExceptionHandler();
	}

	/**
	 * 自定义 TaskDecorator
	 */
	@Bean
	@ConditionalOnMissingBean
	public TaskDecorator taskDecorator() {
		return new ContextCopyingDecorator();
	}

	/**
	 * 拷贝上下文到任务子线程
	 */
	static class ContextCopyingDecorator implements TaskDecorator {

		@Override
		public Runnable decorate(Runnable runnable) {
			// 拷贝请求上下,根据业务需要扩展
			RequestAttributes context = RequestContextHolder.currentRequestAttributes();
			return () -> {
				try {
					RequestContextHolder.setRequestAttributes(context);
					runnable.run();
				}
				finally {
					RequestContextHolder.resetRequestAttributes();
				}
			};
		}

	}

}