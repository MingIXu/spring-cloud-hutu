package com.hutu.cloud.cache.config;

import com.hutu.cloud.cache.message.CustomTopic;
import com.hutu.cloud.cache.message.pub.RedisMessagePublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.List;

/**
 * @author Spike
 **/
@Configuration(proxyBeanMethods = false)
public class RedisPubSubConfiguration {

	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, List<CustomTopic> topics) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		if (topics != null && topics.size() > 0) {
			topics.forEach(topic -> container.addMessageListener(topic.getMessageListenerAdapter(), topic.getTopic()));
		}

		return container;
	}

	@Bean
	public RedisMessagePublisher redisMessagePublisher(RedisTemplate<String, Object> redisTemplate) {
		return new RedisMessagePublisher(redisTemplate);
	}

}
