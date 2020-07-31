package com.hutu;

import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.hutu.handler.WebmvcBlockHandler;
import com.hutu.feign.HutuSentinelFeign;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import javax.servlet.http.HttpServletRequest;

/**
 * Sentinel配置类
 * @author hutu
 *
 * @date 2020/5/21 4:45 下午
 */
@Configuration
public class SentinelAutoConfigure {

    @Bean
    @Scope("prototype")
    @ConditionalOnClass({SphU.class, Feign.class})
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    @Primary
    public Feign.Builder feignSentinelBuilder() {
        return HutuSentinelFeign.builder();
    }

    @Bean
    @ConditionalOnClass(HttpServletRequest.class)
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler() {
        return new WebmvcBlockHandler();
    }

    /*@Bean
    @ConditionalOnClass(ServerResponse.class)
    @ConditionalOnMissingBean
    public BlockRequestHandler blockRequestHandler() {
        return new WebfluxBlockHandler();
    }*/
}
