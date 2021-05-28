package com.hutu.cloud.security.aspect;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.security.annotation.PreAuth;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import com.hutu.cloud.core.exception.GlobalException;
import com.hutu.cloud.security.service.AuthService;
import com.hutu.cloud.common.util.ClassUtil;
import com.hutu.cloud.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.MethodParameter;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * 权限校验切片
 *
 * @author hutu
 * @date 2019/6/19 17:51
 */
@Slf4j
@Aspect
public class AuthAspect {

	/**
	 * 表达式处理
	 */
	private static final ExpressionParser SPEL_PARSER = new SpelExpressionParser();

	/**
	 * 切 方法 和 类上的 @PreAuth 注解
	 * @param point 切点
	 * @return Object
	 * @throws Throwable 没有权限的异常
	 */
	@Around("@annotation(com.hutu.cloud.security.annotation.PreAuth) || "
			+ "@within(com.hutu.cloud.security.annotation.PreAuth)")
	public Object preAuth(ProceedingJoinPoint point) throws Throwable {
		if (handleAuth(point)) {
			return point.proceed();
		}
		throw new GlobalException(CommonStatusEnum.UNAUTHORIZED);
	}

	/**
	 * 处理权限
	 * @param point 切点
	 */
	private boolean handleAuth(ProceedingJoinPoint point) {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		// 读取权限注解，优先方法上，没有则读取类
		PreAuth preAuth = ClassUtil.getAnnotation(method, PreAuth.class);
		if (preAuth.authType().isSkipAllCheck()) {
			return true;
		}
		// 判断表达式
		String condition = preAuth.value();
		if (StrUtil.isNotBlank(condition)) {
			Expression expression = SPEL_PARSER.parseExpression(condition);
			// 方法参数值
			Object[] args = point.getArgs();
			StandardEvaluationContext context = getEvaluationContext(method, args);
			return expression.getValue(context, Boolean.class);
		}
		return false;
	}

	/**
	 * 获取方法上的参数
	 * @param method 方法
	 * @param args 变量
	 * @return {SimpleEvaluationContext}
	 */
	private StandardEvaluationContext getEvaluationContext(Method method, Object[] args) {
		// Singleton.get(AuthService.class); 单例对象或者放入 spring 容器
		// 初始化Sp el表达式上下文，并设置 AuthFun
		StandardEvaluationContext context = new StandardEvaluationContext(new AuthService());
		// 设置表达式支持spring bean
		context.setBeanResolver(new BeanFactoryResolver(SpringUtils.getApplicationContext()));
		for (int i = 0; i < args.length; i++) {
			// 读取方法参数
			MethodParameter methodParam = ClassUtil.getMethodParameter(method, i);
			// 设置方法 参数名和值 为sp el变量
			context.setVariable(methodParam.getParameterName(), args[i]);
		}
		return context;
	}

}
