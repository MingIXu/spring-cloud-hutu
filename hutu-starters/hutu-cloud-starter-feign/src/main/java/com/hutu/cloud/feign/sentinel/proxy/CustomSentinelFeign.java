package com.hutu.cloud.feign.sentinel.proxy;

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import com.hutu.cloud.feign.annotation.CustomFeignClient;
import com.hutu.cloud.feign.fallback.CustomFallbackFactory;
import com.hutu.cloud.feign.properties.FeignProperties;
import feign.Contract;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 支持自动降级注入 重写 {@link com.alibaba.cloud.sentinel.feign.SentinelFeign}
 */
public final class CustomSentinelFeign {

	private CustomSentinelFeign() {

	}

	public static CustomSentinelFeign.Builder builder() {
		return new CustomSentinelFeign.Builder();
	}

	public static final class Builder extends Feign.Builder implements ApplicationContextAware {

		private Contract contract = new Contract.Default();

		private ApplicationContext applicationContext;

		private FeignContext feignContext;

		private FeignProperties feignProperties;

		@Override
		public Feign.Builder invocationHandlerFactory(InvocationHandlerFactory invocationHandlerFactory) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Builder contract(Contract contract) {
			this.contract = contract;
			return this;
		}

		public Builder feignProperties(FeignProperties feignProperties) {
			this.feignProperties = feignProperties;
			return this;
		}

		@Override
		public Feign build() {
			super.invocationHandlerFactory(new InvocationHandlerFactory() {
				@Override
				public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {

					// 查找 FeignClient 上的 降级策略
					FeignClient feignClient = AnnotationUtils.findAnnotation(target.type(), FeignClient.class);
					CustomFeignClient customFeignClient = AnnotationUtils.findAnnotation(target.type(),
							CustomFeignClient.class);
					Class fallback = feignClient.fallback();
					Class fallbackFactory = feignClient.fallbackFactory();

					if (void.class == fallback && customFeignClient != null) {
						fallback = customFeignClient.fallback();
					}

					if (void.class != fallbackFactory && customFeignClient != null) {
						fallbackFactory = customFeignClient.fallbackFactory();
					}

					String beanName = feignClient.contextId();
					if (!StringUtils.hasText(beanName)) {
						beanName = feignClient.name();
					}

					Object fallbackInstance;
					FallbackFactory fallbackFactoryInstance;
					if (void.class != fallback) {
						fallbackInstance = getFromContext(beanName, "fallback", fallback, target.type());
						return new CustomSentinelInvocationHandler(target, dispatch,
								new FallbackFactory.Default(fallbackInstance), feignProperties);
					}

					if (void.class != fallbackFactory) {
						fallbackFactoryInstance = (FallbackFactory) getFromContext(beanName, "fallbackFactory",
								fallbackFactory, FallbackFactory.class);
						return new CustomSentinelInvocationHandler(target, dispatch, fallbackFactoryInstance,
								feignProperties);
					}
					// 默认fallbackFactory
					CustomFallbackFactory customFallbackFactory = new CustomFallbackFactory(target);
					return new CustomSentinelInvocationHandler(target, dispatch, customFallbackFactory,
							feignProperties);
				}

				private Object getFromContext(String name, String type, Class fallbackType, Class targetType) {
					Object fallbackInstance = feignContext.getInstance(name, fallbackType);
					if (fallbackInstance == null) {
						throw new IllegalStateException(String.format(
								"No %s instance of type %s found for feign client %s", type, fallbackType, name));
					}

					if (!targetType.isAssignableFrom(fallbackType)) {
						throw new IllegalStateException(String.format(
								"Incompatible %s instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
								type, fallbackType, targetType, name));
					}
					return fallbackInstance;
				}
			});

			super.contract(new SentinelContractHolder(contract));
			return super.build();
		}

		private Object getFieldValue(Object instance, String fieldName) {
			Field field = ReflectionUtils.findField(instance.getClass(), fieldName);
			field.setAccessible(true);
			try {
				return field.get(instance);
			}
			catch (IllegalAccessException e) {
				// ignore
			}
			return null;
		}

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.applicationContext = applicationContext;
			feignContext = this.applicationContext.getBean(FeignContext.class);
		}

	}

}
