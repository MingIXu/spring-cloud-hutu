package com.hutu.log.config;

import com.hutu.api.RemoteLogService;
import com.hutu.common.constant.CommonConstant;
import com.hutu.log.aspect.ApiLogAspect;
import com.hutu.log.event.ApiLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志工具自动配置
 * @author hutu
 */
@Order
@EnableAsync
@Configuration
@EnableFeignClients(CommonConstant.BASE_PACKAGES)
@ConditionalOnWebApplication
public class LogAutoConfiguration {

    @Bean
    public ApiLogAspect apiLogAspect() {
        return new ApiLogAspect();
    }

    @Bean
    public ApiLogListener sysLogListener(RemoteLogService remoteLogService) {
        return new ApiLogListener(remoteLogService);
    }
}
