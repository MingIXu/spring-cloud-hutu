package com.hutu.swagger;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;

import java.util.List;

/**
 * Swagger工具类
 *
 * @author hutu
 */
public class SwaggerUtil {

	/**
	 * 获取包集合
	 *
	 * @param basePackages 多个包名集合
	 */
	public static Predicate<RequestHandler> basePackages(final List<String> basePackages) {

		Predicate<RequestHandler> predicate = null;
		for (int i = basePackages.size() -1; i >= 0 ; i--) {
			String strBasePackage = basePackages.get(i);
			if(StrUtil.isNotBlank(strBasePackage)){
				Predicate<RequestHandler> tempPredicate = RequestHandlerSelectors.basePackage(strBasePackage);
				predicate = predicate == null ? tempPredicate : Predicates.or(tempPredicate,predicate);
			}
		}
		if(predicate == null){
			throw new NullPointerException("basePackage配置不正确");
		}
		return predicate;
//		return input -> declaringClass(input).transform(handlerPackage(basePackages)).or(true);
	}

	private static Function<Class<?>, Boolean> handlerPackage(final List<String> basePackages) {
		return input -> {
			// 循环判断匹配
			for (String strPackage : basePackages) {

				boolean isMatch = input.getPackage().getName().startsWith(strPackage);
				if (isMatch) {
					return true;
				}
			}
			return false;
		};
	}

	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.fromNullable(input.declaringClass());
	}

}
