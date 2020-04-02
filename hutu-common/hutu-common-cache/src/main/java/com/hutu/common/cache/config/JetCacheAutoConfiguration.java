package com.hutu.common.cache.config;

import org.springframework.context.annotation.Configuration;

/**
 * 初始化 redis 相关
 */
@Configuration
public class JetCacheAutoConfiguration{

//    /**
//     * RedisTemplate配置
//     * @param factory
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(redisObjectSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    /**
//     * Redis repository redis repository.
//     *
//     * @param redisTemplate the redis template
//     * @return the redis repository
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public RedisRepository redisRepository(RedisTemplate<String, Object> redisTemplate) {
//        return new RedisRepository(redisTemplate);
//    }

}
