package com.hutu.cache.annotation;

import com.hutu.cache.config.TedisAutoConfiguration;
import com.hutu.cache.rest.CacheManagerRest;
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
@Import({TedisAutoConfiguration.class, CacheManagerRest.class})
@Documented
@Inherited
public @interface EnableHutuCache {
}
