package com.hutu.cloud.sensitive.annotation;

import com.hutu.cloud.sensitive.enums.SliceTypeEnum;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典转换注解
 *
 * @author hutu
 * @date 2021/4/8 5:16 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Dictionary {

	@AliasFor("id")
	String value() default "";

	@AliasFor("value")
	String id() default "";

	String parentKey() default "";

	String render() default "";

	byte sliceType() default SliceTypeEnum.CHILD_ALL;

	boolean multiple() default false;

}