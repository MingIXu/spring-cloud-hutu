package org.springframework.cloud.openfeign;

import feign.Target;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author 冷冷
 * <p>
 * 默认 Fallback，避免写过多fallback类
 */
@AllArgsConstructor
public class DefaultFallbackFactory<T> implements FallbackFactory<T> {
	private final Target<T> target;

	@Override
	@SuppressWarnings("unchecked")
	public T create(Throwable cause) {
		final Class<T> targetType = target.type();
		final String targetName = target.name();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetType);
		enhancer.setUseCache(true);
		enhancer.setCallback(new DefaultFeignFallback<>(targetType, targetName, cause));
		return (T) enhancer.create();
	}
}
