package com.hutu.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.SubscribableChannel;

/**
 * 默认定义四个消息通道对应四类消息
 *
 * @author hutu
 * @date 2020/5/29 2:45 下午
 */
public interface EhSink {
    String INPUT1 = "input1";
    String INPUT2 = "input2";
    String INPUT3 = "input3";
    String INPUT4 = "input4";

    /**
     * 此通道建议用作 broker push 模式
     * @return MessageChannel
     */
    @Input(INPUT1)
    SubscribableChannel input1();

    /**
     * 此通道建议用作 broker push 模式(顺序)
     * @return MessageChannel
     */
    @Input(INPUT2)
    SubscribableChannel input2();

    /**
     * 此通道建议用作 customer pull 模式
     * @return MessageChannel
     */
    @Input(INPUT3)
    SubscribableChannel input3();

    /**
     * 此通道建议用作需要事物支持的消息
     * @return MessageChannel
     */
    @Input(INPUT4)
    SubscribableChannel input4();

}
