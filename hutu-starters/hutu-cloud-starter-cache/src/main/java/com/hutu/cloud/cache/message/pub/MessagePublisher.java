package com.hutu.cloud.cache.message.pub;

/**
 * @author Spike
 **/
public interface MessagePublisher {

	void publish(String channel, Object message);

}
