package com.hutu.cloud.cache.message;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheGetResult;
import com.alicp.jetcache.MultiLevelCache;
import com.alicp.jetcache.RefreshCache;
import com.alicp.jetcache.anno.method.CacheHandler;
import com.alicp.jetcache.anno.support.SimpleCacheManager;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.hutu.cloud.cache.dto.CacheDTO;
import com.hutu.cloud.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import javax.annotation.PostConstruct;

/**
 * 基于redis的pub/sub机制同步本地缓存，不保证消息一定可达
 *
 * @author hutu
 * @date 2020/10/9 10:31 上午
 */
@Slf4j
@Deprecated
public class DictionaryRedisSubscriber {

	private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

	private ChannelTopic customerChannel;

	private SpringConfigProvider springConfigProvider;

	public DictionaryRedisSubscriber(ReactiveStringRedisTemplate reactiveStringRedisTemplate, ChannelTopic channelTopic,
			SpringConfigProvider springConfigProvider) {
		this.reactiveStringRedisTemplate = reactiveStringRedisTemplate;
		this.customerChannel = channelTopic;
		this.springConfigProvider = springConfigProvider;

	}

	@PostConstruct
	public void init() {
		reactiveStringRedisTemplate.listenTo(customerChannel).cast(ReactiveSubscription.ChannelMessage.class).map(m -> {
			log.info("received sub message: {}", m);
			CacheDTO cacheDTO = JsonUtil.parse((String) m.getMessage(), CacheDTO.class);
			Object key = cacheDTO.getCacheMessage().getKeys()[0];

			SimpleCacheManager cacheManager = springConfigProvider.getCacheManager();
			Cache cache = cacheManager.getCacheWithoutCreate(cacheDTO.getArea(), cacheDTO.getCacheName());
			if (cache != null) {
				CacheHandler.CacheHandlerRefreshCache refreshCache = (CacheHandler.CacheHandlerRefreshCache) cache;

				Cache targetCache = refreshCache.getTargetCache();
				// 如果是二级缓存则刷新本地缓存
				if (targetCache instanceof MultiLevelCache) {
					refreshUpperCaches(targetCache, key);
				}

			}
			return m;
		}).subscribe();
	}

	/**
	 * 刷新逻辑参考 {@link RefreshCache.RefreshTask#refreshUpperCaches(java.lang.Object)}
	 * @param cache
	 * @param key
	 */
	private void refreshUpperCaches(Cache cache, Object key) {
		MultiLevelCache targetCache = (MultiLevelCache) cache;
		Cache[] caches = targetCache.caches();
		int len = caches.length;

		CacheGetResult cacheGetResult = caches[len - 1].GET(key);
		if (!cacheGetResult.isSuccess()) {
			return;
		}
		for (int i = 0; i < len - 1; i++) {
			caches[i].PUT(key, cacheGetResult.getValue());
		}
	}

}