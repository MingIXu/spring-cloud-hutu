package com.hutu.cloud.feign.fallback;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.core.R;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * feign 的默认降级处理
 *
 * @author hutu
 * @date 2021/4/23 11:24 上午
 */
@Slf4j
@AllArgsConstructor
public class CustomFeignFallback<T> implements MethodInterceptor {

	private final Class<T> targetType;

	private final String targetName;

	private final Throwable cause;

	private final static String CODE = "code";

	@Nullable
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		String errorMessage = cause.getMessage();
		log.error("CustomFeignFallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(),
				targetName, errorMessage);
		cause.printStackTrace();
		Class<?> returnType = method.getReturnType();
		// 暂时不支持 flux，rx，异步等，返回值不是 R，直接返回 null。
		if (R.class != returnType) {
			return null;
		}
		// 非 FeignException
		if (!(cause instanceof FeignException)) {
			return R.failed(errorMessage);
		}
		FeignException exception = (FeignException) cause;

		if (StrUtil.isEmpty(exception.contentUTF8())) {
			R.failed(CommonStatusEnum.SERVICE_FALLBACK);
		}

		return R.failed(exception.contentUTF8());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CustomFeignFallback<?> that = (CustomFeignFallback<?>) o;
		return targetType.equals(that.targetType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(targetType);
	}

}
