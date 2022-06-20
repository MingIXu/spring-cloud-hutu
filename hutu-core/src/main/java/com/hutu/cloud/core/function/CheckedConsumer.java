package com.hutu.cloud.core.function;

import org.springframework.lang.Nullable;

/**
 * 会抛异常的 consumer
 *
 * @author hutu
 * @date 2021/7/21 2:30 下午
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

	/**
	 * Run the Consumer
	 * @param t T
	 * @throws Throwable UncheckedException
	 */
	void accept(@Nullable T t) throws Throwable;

}
