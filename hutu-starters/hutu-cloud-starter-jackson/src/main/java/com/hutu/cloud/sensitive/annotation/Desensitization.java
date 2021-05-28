package com.hutu.cloud.sensitive.annotation;

import com.hutu.cloud.sensitive.enums.RuleTypeEnum;
import com.hutu.cloud.sensitive.enums.SensitiveTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感字段标记注解
 *
 * @author hutu
 * @date 2021/4/7 3:18 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Desensitization {

	/**
	 * 脱敏类型
	 */
	SensitiveTypeEnum type();

	/**
	 * 脱敏规则 说明：只有在脱敏规则为自定义情况下下面参数才有效
	 */
	RuleTypeEnum rule() default RuleTypeEnum.NONE;

	/**
	 * 隐藏或展示字段长度，适用于规则 {@link RuleTypeEnum#DISPLAY_HEAD} {@link RuleTypeEnum#DISPLAY_TAIL}
	 * {@link RuleTypeEnum#HIDE_HEAD} {@link RuleTypeEnum#HIDE_TAIL}
	 */
	int length() default 0;

	/**
	 * 头部隐藏或展示字段长度，适用于规则 {@link RuleTypeEnum#DISPLAY_HEAD_AND_TAIL}
	 * {@link RuleTypeEnum#HIDE_HEAD_AND_TAIL}
	 */
	int headLength() default 0;

	/**
	 * 尾部隐藏或展示字段长度，适用于规则 {@link RuleTypeEnum#DISPLAY_HEAD_AND_TAIL}
	 * {@link RuleTypeEnum#HIDE_HEAD_AND_TAIL}
	 */
	int tailLength() default 0;

}