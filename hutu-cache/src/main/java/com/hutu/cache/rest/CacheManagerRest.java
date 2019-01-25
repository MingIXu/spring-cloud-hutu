package com.hutu.cache.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.hutu.cache.constant.CacheConstant.CACHE_NAME;

/**
 * 缓存管理
 *
 * @author hutu
 * @date 2019/1/25
 */
@RestController
@RequestMapping("cache")
public class CacheManagerRest {

    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 获取缓存列表
     * @return
     */
    @GetMapping("list")
    public List getCache() {
        Set keys = redisTemplate.keys(CACHE_NAME+"*");
        List list = redisTemplate.opsForValue().multiGet(keys);
        return list;
    }
}
