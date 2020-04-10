package com.hutu.security.config;

import com.hutu.security.aspect.AuthAspect;
import com.hutu.security.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置类
 * @author hutu
 * @date 2019/7/16 14:56
 */
@Order
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    /**
     * 注册token拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor());
    }
    /**
     * 注入鉴权aop
     *
     * @return AuthAspect
     */
    @Bean
    public AuthAspect authAspect() {
        return new AuthAspect();
    }
}
