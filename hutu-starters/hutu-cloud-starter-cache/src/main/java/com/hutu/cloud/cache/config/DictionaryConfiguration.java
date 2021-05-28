package com.hutu.cloud.cache.config;

import com.hutu.cloud.cache.message.CustomTopic;
import com.hutu.cloud.cache.dictionary.DictionarySubscriber;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author Spike
 **/
@Configuration(proxyBeanMethods = false)
public class DictionaryConfiguration {

	@Bean(name = "dicListenerAdapter")
	public MessageListenerAdapter listenerAdapter(RedisTemplate<String, Object> redisTemplate) {
		return new MessageListenerAdapter(new DictionarySubscriber(redisTemplate));
	}

	@Bean(name = "dicTopic")
	public ChannelTopic channelTopic() {
		return new ChannelTopic("/cloud/core/dic");
	}

	@Bean
	public CustomTopic customTopic(@Qualifier("dicTopic") ChannelTopic channelTopic,
			@Qualifier("dicListenerAdapter") MessageListenerAdapter messageListenerAdapter) {
		return new CustomTopic(channelTopic, messageListenerAdapter);
	}

}
