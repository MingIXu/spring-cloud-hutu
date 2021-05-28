package com.hutu.cloud.cache.message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Spike
 **/
@Getter
@Setter
@Accessors(chain = true)
public class CustomMessage<T> {

	private T body;

	private String channel;

}
