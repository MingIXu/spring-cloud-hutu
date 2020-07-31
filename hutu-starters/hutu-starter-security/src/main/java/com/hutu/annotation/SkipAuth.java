package com.hutu.annotation;

import java.lang.annotation.*;

/**
 * api接口，忽略Token验证
 * @author hutu
 * @date 2019-12-11 11:42
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipAuth {

}
