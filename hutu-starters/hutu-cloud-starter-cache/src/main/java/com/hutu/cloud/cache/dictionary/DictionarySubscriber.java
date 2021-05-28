package com.hutu.cloud.cache.dictionary;

import com.hutu.cloud.cache.message.sub.AbstractRedisMessageSubscriber;
import com.hutu.cloud.cache.message.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * todo: 字典的订阅
 *
 * @author Spike
 *
 **/
@Slf4j
public class DictionarySubscriber extends AbstractRedisMessageSubscriber<String> {

	public DictionarySubscriber(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
	}

	@Override
	public void onMessage(CustomMessage<String> message) {
		log.info("DictionarySubscriber message=[{}], channel=[{}]", message.getBody(), message.getChannel());
	}

}
