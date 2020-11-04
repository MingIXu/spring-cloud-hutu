package com.hutu.cache.message;

import com.alicp.jetcache.support.CacheMessage;
import com.alicp.jetcache.support.CacheMessagePublisher;
import com.hutu.cache.dto.CacheDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现jetcache的发布接口，当缓存发生操作事件时，会调用该实现接口
 *
 * @author hutu
 * @date 2020/10/9 10:35 上午
 */
@Slf4j
public class CustomCacheMessagePublisher implements CacheMessagePublisher {

    private DictionaryRedisPublisher dictionaryRedisPublisher;

    public CustomCacheMessagePublisher(DictionaryRedisPublisher dictionaryRedisPublisher){
        this.dictionaryRedisPublisher = dictionaryRedisPublisher;
    }

    @Override
    public void publish(String area, String cacheName, CacheMessage cacheMessage) {

        dictionaryRedisPublisher.publish(new CacheDTO(area,cacheName,cacheMessage));
    }
}
