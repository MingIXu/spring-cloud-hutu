package com.hutu.common.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启自定义缓存配置
 *
 * @author hutu
 * @date 2018/8/13 14:55
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({TedisAutoConfiguration.class})
@Documented
@Inherited
public @interface EnableHutuCache {
}
