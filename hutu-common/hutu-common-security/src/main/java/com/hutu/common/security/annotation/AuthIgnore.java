package com.hutu.common.security.annotation;

import java.lang.annotation.*;

/**
 * api接口，忽略Token验证
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {

}
