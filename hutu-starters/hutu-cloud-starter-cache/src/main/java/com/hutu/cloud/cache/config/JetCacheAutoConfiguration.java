package com.hutu.cloud.cache.config;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.hutu.cloud.core.constant.CommonConstant;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 配置jetcache,默认使其生效
 *
 * @author hutu
 * @date 2020/6/23 3:45 下午
 */

@Configuration(proxyBeanMethods = false)
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = { CommonConstant.BASE_PACKAGES })
@AutoConfigureBefore(RedisAutoConfiguration.class)
@Import({ RedisTemplateConfiguration.class, RedisPubSubConfiguration.class, DictionaryConfiguration.class })
public class JetCacheAutoConfiguration {

	// final static String DICTIONARY_SYNC_TOPIC = "dictionarySyncTopic";

	// @Bean
	// public CustomCacheMessagePublisher
	// customCacheMessagePublisher(DictionaryRedisPublisher dictionaryRedisPublisher) {
	// return new CustomCacheMessagePublisher(dictionaryRedisPublisher);
	// }
	// @Bean
	// public ChannelTopic channelTopic(){
	// return new ChannelTopic(DICTIONARY_SYNC_TOPIC);
	// }
	//
	// @Bean
	// public DictionaryRedisPublisher customerRedisPublisher(StringRedisTemplate
	// stringRedisTemplate, ChannelTopic channelTopic) {
	// return new DictionaryRedisPublisher(stringRedisTemplate,channelTopic);
	// }
	//
	// @Bean
	// public DictionaryRedisSubscriber
	// customerRedisSubscriber(ReactiveStringRedisTemplate reactiveStringRedisTemplate,
	// ChannelTopic channelTopic, SpringConfigProvider springConfigProvider) {
	// return new
	// DictionaryRedisSubscriber(reactiveStringRedisTemplate,channelTopic,springConfigProvider);
	// }
	//
	// /**
	// * 创建字典缓存对象
	// */
	// @CreateCache(name = "dicCache", cacheType = CacheType.BOTH, localLimit = 10000,
	// localExpire = 1000)
	// private Cache<String, String> dicCache;
	//
	// @Bean
	// public DictionaryCacheUtil dictionaryCacheUtil(){
	// return new DictionaryCacheUtil().setDicCache(dicCache);
	// }

}