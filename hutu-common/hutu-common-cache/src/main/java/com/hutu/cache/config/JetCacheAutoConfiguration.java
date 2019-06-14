package com.hutu.cache.config;

import com.alicp.jetcache.autoconfigure.JedisPoolFactory;
import com.alicp.jetcache.autoconfigure.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import redis.clients.jedis.JedisPool;

/**
 * 初始化 redis 相关
 */
@Configuration
public class JetCacheAutoConfiguration{
//    @Bean(name = "defaultPool")
//    @DependsOn(RedisAutoConfiguration.AUTO_INIT_BEAN_NAME)
//    public JedisPoolFactory defaultPool() {
//        return new JedisPoolFactory("remote.default", JedisPool.class);
//    }
}
