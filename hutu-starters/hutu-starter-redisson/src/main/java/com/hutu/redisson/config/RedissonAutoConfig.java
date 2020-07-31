package com.hutu.redisson.config;

import com.hutu.redisson.lock.RedissonDistributedLock;
import com.hutu.core.lock.DistributedLock;
import com.hutu.redisson.aspect.LockAspect;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置
 *
 * @author hutu
 * @date 2020/6/22 2:29 下午
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class RedissonAutoConfig {

    @Bean
    public DistributedLock distributedLock(RedissonClient redissonClient) {
        return new RedissonDistributedLock(redissonClient);
    }

    @Bean
    public LockAspect lockAspect(DistributedLock distributedLock) {
        return new LockAspect(distributedLock);
    }
}