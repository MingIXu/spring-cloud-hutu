package com.hutu.cloud.cache.message;

import com.hutu.cloud.cache.dto.CacheDTO;
import com.hutu.cloud.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * 基于redis的pub/sub机制同步本地缓存，不保证消息一定可达
 *
 * @author hutu
 * @date 2020/10/9 10:31 上午
 */
@Slf4j
@Deprecated
public class DictionaryRedisPublisher {

	private StringRedisTemplate redisTemplate;

	private ChannelTopic customerChannel;

	public DictionaryRedisPublisher(StringRedisTemplate redisTemplate, ChannelTopic customerChannel) {
		this.redisTemplate = redisTemplate;
		this.customerChannel = customerChannel;
	}

	public void publish(CacheDTO cacheDTO) {
		log.info(" Sending message: {} ", JsonUtil.toJsonPrettyString(cacheDTO));
		redisTemplate.convertAndSend(customerChannel.getTopic(), JsonUtil.toJsonString(cacheDTO));
	}

}