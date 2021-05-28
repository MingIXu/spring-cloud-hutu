package com.hutu.cloud.cache.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 关联topic和adapter 注册到
 * <p>
 * RedisMessageListenerContainer
 * </p>
 *
 * @author Spike
 *
 **/
@Setter
@Getter
@AllArgsConstructor
public class CustomTopic {

	private Topic topic;

	private MessageListenerAdapter messageListenerAdapter;

}
