package com.hutu.common.cache.config;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.alicp.jetcache.autoconfigure.LettuceFactory;
import com.alicp.jetcache.autoconfigure.RedisLettuceAutoConfiguration;
import com.alicp.jetcache.redis.lettuce.LettuceConnectionManager;
import io.lettuce.core.RedisClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 初始化 redis 相关
 */
@Configuration
public class JetCacheAutoConfiguration{
    @Bean(name = "defaultClient")
    @DependsOn(RedisLettuceAutoConfiguration.AUTO_INIT_BEAN_NAME)
    public LettuceFactory defaultClient() {
        return new LettuceFactory("remote.default", RedisClient.class);
    }
//    @CreateCache(expire = 1000)
//    private Cache cache;
//    @Bean(name = "defaultClient")
//    @DependsOn(RedisLettuceAutoConfiguration.AUTO_INIT_BEAN_NAME)
//    public RedisClient defaultClient() {
//        RedisClient  redisClient = (RedisClient) cache.unwrap(RedisClient.class);
//        return redisClient;
//    }

}
