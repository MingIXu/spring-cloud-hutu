package com.hutu.cloud.stream.function;

import com.hutu.cloud.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.core.annotation.Order;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.Map;

@Order
@AllArgsConstructor
public class TempestStreamBridge {

	private final StreamBridge streamBridge;

	public boolean publish(String bindingName, Object msg, String tag, int delayLevel) {
		Message<Object> message = MessageBuilder.withPayload(msg).setHeader(MessageConst.PROPERTY_TAGS, tag)
				.setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delayLevel).build();
		return streamBridge.send(bindingName, message);
	}

	public boolean publish(String bindingName, Object msg) {
		return this.publish(bindingName, msg, null, 0);
	}

	public boolean publish(String bindingName, Object msg, String tag) {
		return this.publish(bindingName, msg, tag, 0);
	}

	public boolean publish(String bindingName, Object msg, int delayLevel) {
		return this.publish(bindingName, msg, null, delayLevel);
	}

	public boolean publishTransaction(String bindingName, Object msg, String tag, Object arg) {
		return publishTransactionWithHeaders(bindingName, msg, tag, arg, null);
	}

	public boolean publishTransactionWithHeaders(String bindingName, Object msg, String tag, Object arg,
			Map<String, String> headers) {
		MessageBuilder<Object> builder = MessageBuilder.withPayload(msg).setHeader(MessageConst.PROPERTY_TAGS, tag)
				.setHeader("TRANSACTIONAL_ARGS", JsonUtil.toJson(arg));
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> e : headers.entrySet()) {
				builder.setHeader(e.getKey(), e.getValue());
			}
		}
		Message<Object> message = builder.build();
		return streamBridge.send(bindingName, message);
	}

}
