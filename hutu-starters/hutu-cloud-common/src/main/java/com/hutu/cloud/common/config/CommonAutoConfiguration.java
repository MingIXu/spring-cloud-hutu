package com.hutu.cloud.common.config;

import com.hutu.cloud.common.util.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author hutu
 * @date 2021/5/28 10:19 上午
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CommonAutoConfiguration {

    @Bean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }
}
