package com.hutu.common.log.annotation;

import com.hutu.common.log.enums.OperateEnum;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author hutu
 * @date 2018/3/28 10:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
	/**
	 * 接口信息
	 */
	String value() default "";

	/**
	 * 操作类型
	 */
	OperateEnum type() default OperateEnum.OTHER;

	/**
	 * 所属模块
	 */
	String module() default "";
}
