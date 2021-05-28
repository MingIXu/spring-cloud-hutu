package com.hutu.cloud.stream.converter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;

public class MappingHessianConverter extends AbstractMessageConverter {


	@Override
	protected boolean supports(Class<?> aClass) {
		return true;
	}

	@Override
	protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
		Object payload = message.getPayload();
		Object obj = null;
		// TODO
		return obj;
	}

	@Override
	protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
		// TODO
		return null;
	}

}
