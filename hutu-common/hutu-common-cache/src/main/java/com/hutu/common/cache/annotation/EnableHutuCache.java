package com.hutu.common.cache.annotation;

import com.hutu.common.cache.config.JetCacheAutoConfiguration;
import com.hutu.common.cache.rest.CacheManagerRest;
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
@Import({JetCacheAutoConfiguration.class,CacheManagerRest.class})
@Documented
public @interface EnableHutuCache {
}
