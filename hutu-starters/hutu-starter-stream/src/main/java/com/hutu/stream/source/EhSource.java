package com.hutu.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 默认定义三个消息通道对应三类消息
 *
 * @author hutu
 * @date 2020/5/29 2:45 下午
 */
public interface EhSource {
    String OUTPUT1 = "output1";
    String OUTPUT2 = "output2";
    String OUTPUT3 = "output3";

    /**
     * 此通道建议用作 broker push 模式
     * @return MessageChannel
     */
    @Output(OUTPUT1)
    MessageChannel output1();
    /**
     * 此通道建议用作 customer pull 模式
     * @return MessageChannel
     */
    @Output(OUTPUT2)
    MessageChannel output2();
    /**
     * 此通道建议用作需要事物支持的消息
     * @return MessageChannel
     */
    @Output(OUTPUT3)
    MessageChannel output3();

}
