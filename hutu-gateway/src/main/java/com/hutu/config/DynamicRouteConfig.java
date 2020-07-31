package com.hutu.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.hutu.nacos.NacosRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由配置
 *
 * @author hutu
 * @date 2020/5/21 2:52 下午
 */
@Configuration
@ConditionalOnProperty(prefix = "hutu.gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class DynamicRouteConfig {
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Nacos实现方式
     */
    @Configuration
    @ConditionalOnProperty(prefix = "hutu.gateway.dynamicRoute", name = "dataType", havingValue = "nacos", matchIfMissing = true)
    public class NacosDynRoute {

        @Value("${spring.cloud.nacos.dataId}")
        public String dataId;
        @Value("${spring.cloud.nacos.group}")
        public String group;

        @Bean
        public NacosRouteDefinitionRepository nacosRouteDefinitionRepository(NacosConfigManager nacosConfigManager) {
            return new NacosRouteDefinitionRepository(publisher, nacosConfigManager,dataId,group);
        }
    }
}
