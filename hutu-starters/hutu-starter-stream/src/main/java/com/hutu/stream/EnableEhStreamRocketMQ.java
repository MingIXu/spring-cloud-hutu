package com.hutu.stream;

import com.hutu.stream.sink.EhSink;
import com.hutu.stream.source.EhSource;
import org.springframework.cloud.stream.annotation.EnableBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启MQ配置
 *
 * @author hutu
 * @date 2020/5/29 3:36 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableBinding({EhSource.class, EhSink.class})
public @interface EnableEhStreamRocketMQ {

}
