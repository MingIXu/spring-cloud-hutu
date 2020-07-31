package com.hutu.annotation;

import com.hutu.enums.OperateEnum;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author hutu
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {
	/**
	 * 接口信息
	 */
	String value() default "";

	/**
	 * 操作类型
	 */
	OperateEnum type() default OperateEnum.OTHER;

}
