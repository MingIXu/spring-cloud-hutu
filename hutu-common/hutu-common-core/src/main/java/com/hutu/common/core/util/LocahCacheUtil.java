package com.hutu.common.core.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 缓存操作类 参考：https://blog.csdn.net/xiaolyuh123/article/details/78794012
 *
 * @author hutu
 * @date 2018/6/13 9:01
 */
public class LocahCacheUtil {
    private static final Logger logger = LoggerFactory.getLogger(LocahCacheUtil.class);
    private static final Long CACHE_MAX_SIZE = 10000L;
    private static final int CACHE_EXPIRE = 1;
    /**
     * 基于大小（默认）
     */
    private static final Cache<String, Object> SIZE_CACHE = Caffeine.newBuilder().maximumSize(CACHE_MAX_SIZE)
            .build(LocahCacheUtil::createNullIfKeyNoValue);
    /**
     * 基于时间
     */
    private static final Cache<String, Object> EXPIRE_CACHE = Caffeine.newBuilder().expireAfterWrite(CACHE_EXPIRE, TimeUnit.MINUTES)
            .maximumSize(CACHE_MAX_SIZE).build(LocahCacheUtil::createNullIfKeyNoValue);

    public static Cache getCache() {
        return SIZE_CACHE;
    }

    public static Cache getExpireCache() {
        return EXPIRE_CACHE;
    }

    public static Object get(String key) {
        return getCache().getIfPresent(key);
    }

    public static void put(String key, Object value) {
        getCache().put(key, value);
    }

    public static Object createNullIfKeyNoValue(String key) {
        logger.info("the key: {} no value in cache", key);
        return null;
    }

    public static void main1(String[] args) {
        //获取当前的时间戳作为key
        put("a", "123");
        get("a");
        Cache cache = getCache();
        String a = (String) get("a");
        String b = (String) get("b");
        cache.cleanUp();
        logger.info(a);
        logger.info(b);

    }
}
