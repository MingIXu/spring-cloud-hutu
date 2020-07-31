//package com.hutu.stream.service;
//
//import com.hutu.stream.source.EhSource;
//import org.apache.rocketmq.common.message.MessageConst;
//import org.apache.rocketmq.spring.support.RocketMQHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.MimeTypeUtils;
//
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * 简单消息发送服务
// *
// * @author hutu
// * @date 2020/5/29 3:30 下午
// */
//@Service
//public class SenderService {
//
//	@Autowired
//	private EhSource source;
//
//	public void send(String msg) throws Exception {
//		source.output1().send(MessageBuilder.withPayload(msg).build());
//	}
//
//	public <T> void sendWithTags(T msg, String tag) throws Exception {
//		Message message = MessageBuilder.createMessage(msg,
//				new MessageHeaders(Stream.of(tag).collect(Collectors
//						.toMap(str -> MessageConst.PROPERTY_TAGS, String::toString))));
//		source.output1().send(message);
//	}
//
//	public <T> void sendObject(T msg, String tag) throws Exception {
//		Message message = MessageBuilder.withPayload(msg)
//				.setHeader(MessageConst.PROPERTY_TAGS, tag)
//				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
//				.build();
//		source.output1().send(message);
//	}
//
//	public <T> void sendTransactionalMsg(T msg, int num) throws Exception {
//		MessageBuilder builder = MessageBuilder.withPayload(msg)
//				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
//		builder.setHeader(RocketMQHeaders.TAGS, "binder");
//		Message message = builder.build();
//		source.output2().send(message);
//	}
//
//}
