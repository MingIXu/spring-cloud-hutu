package com.hutu.cache.config;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.hutu.core.constant.CommonConstant;
import org.springframework.context.annotation.Configuration;


/**
 * 配置jetcache,默认使其生效
 *
 * @author hutu
 * @date 2020/6/23 3:45 下午
 */

@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = {CommonConstant.BASE_PACKAGES})
public class JetcacheAutoConfig {

}
