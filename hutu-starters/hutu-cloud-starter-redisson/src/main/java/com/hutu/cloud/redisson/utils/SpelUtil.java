package com.hutu.cloud.redisson.utils;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 分布式锁相关工具
 *
 * @author hutu
 * @date 2020/6/22 4:25 下午
 */
public class SpelUtil {

	/**
	 * 用于SpEL表达式解析.
	 */
	private final static SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

	/**
	 * 用于获取方法参数定义名字.
	 */
	private final static DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

	/**
	 * 解析spEL表达式
	 */
	public static String getValBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
		// 获取方法形参名数组
		String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
		if (paramNames != null && paramNames.length > 0) {
			Expression expression = spelExpressionParser.parseExpression(spEL);
			// spring的表达式上下文对象
			EvaluationContext context = new StandardEvaluationContext();
			// 给上下文赋值
			for (int i = 0; i < args.length; i++) {
				context.setVariable(paramNames[i], args[i]);
			}
			return expression.getValue(context).toString();
		}
		return null;
	}

}
