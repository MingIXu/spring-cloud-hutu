package com.hutu.cloud.security.annotation;

import com.hutu.cloud.security.enums.AuthType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要的权限注解
 * @author hutu
 * @date 2018/3/28 10:00
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuth {

	String value() default "";

	AuthType authType() default AuthType.CLIENT;

}
