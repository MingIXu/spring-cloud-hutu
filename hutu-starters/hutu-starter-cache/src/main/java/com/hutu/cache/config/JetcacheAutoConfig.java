package com.hutu.cache.config;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.hutu.cache.message.CustomCacheMessagePublisher;
import com.hutu.cache.message.DictionaryRedisPublisher;
import com.hutu.cache.message.DictionaryRedisSubscriber;
import com.hutu.cache.util.DictionaryCacheUtil;
import com.hutu.core.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;


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

//    final static String DICTIONARY_SYNC_TOPIC = "dictionarySyncTopic";
//
//    @Bean
//    public CustomCacheMessagePublisher customCacheMessagePublisher(DictionaryRedisPublisher dictionaryRedisPublisher) {
//        return new CustomCacheMessagePublisher(dictionaryRedisPublisher);
//    }
//    @Bean
//    public ChannelTopic channelTopic(){
//        return new ChannelTopic(DICTIONARY_SYNC_TOPIC);
//    }
//
//    @Bean
//    public DictionaryRedisPublisher customerRedisPublisher(StringRedisTemplate stringRedisTemplate, ChannelTopic channelTopic) {
//        return new DictionaryRedisPublisher(stringRedisTemplate,channelTopic);
//    }
//
//    @Bean
//    public DictionaryRedisSubscriber customerRedisSubscriber(ReactiveStringRedisTemplate reactiveStringRedisTemplate, ChannelTopic channelTopic, SpringConfigProvider springConfigProvider) {
//        return new DictionaryRedisSubscriber(reactiveStringRedisTemplate,channelTopic,springConfigProvider);
//    }
//
//    /**
//     * 创建字典缓存对象
//     */
//    @CreateCache(name = "dicCache", cacheType = CacheType.BOTH, expire = CacheConsts.UNDEFINED_INT, localLimit = 10000, localExpire = 1000)
//    private Cache<String, String> dicCache;
//
//    @Bean
//    public DictionaryCacheUtil dictionaryCacheUtil(){
//        return new DictionaryCacheUtil(dicCache);
//    }

}