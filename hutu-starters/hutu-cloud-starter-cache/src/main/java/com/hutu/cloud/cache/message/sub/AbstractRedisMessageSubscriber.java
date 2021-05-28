package com.hutu.cloud.cache.message.sub;

import com.hutu.cloud.cache.message.CustomMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Spike
 **/
@RequiredArgsConstructor
public abstract class AbstractRedisMessageSubscriber<T> implements MessageListener {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		T body = (T) redisTemplate.getValueSerializer().deserialize(message.getBody());
		String channel = (String) redisTemplate.getKeySerializer().deserialize(message.getChannel());

		CustomMessage<T> m = new CustomMessage<T>().setBody(body).setChannel(channel);
		onMessage(m);
	}

	protected abstract void onMessage(CustomMessage<T> message);

}
