package com.hutu.cloud.cache.message.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Spike
 **/
@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void publish(String channel, Object message) {
		redisTemplate.convertAndSend(channel, message);
	}

}
