package com.hutu.config;

import com.hutu.aspect.AuthAspect;
import com.hutu.constant.SecurityConstant;
import com.hutu.core.constant.CommonConstant;
import com.hutu.interceptor.AuthorizationInterceptor;
import com.hutu.properties.SecurityProperties;
import com.hutu.service.DefaultSecurityServiceImpl;
import com.hutu.service.SecurityService;
import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 安全配置类
 *
 * @author hutu
 * @date 2019/7/16 14:56
 */
@Order
@Configuration
@AllArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private SecurityProperties securityProperties;

    /**
     * 注册拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(securityProperties))
                .excludePathPatterns(SecurityConstant.DEFAULT_EXCLUDE_PATTERNS);
    }

    @Bean
    @ConditionalOnMissingBean(SecurityService.class)
    public SecurityService permissionService() {
        return new DefaultSecurityServiceImpl();
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

    /**
     * 解决feign中header传递问题
     */
    @Bean
    public RequestInterceptor headerInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        // feign都是内部调用，修改鉴权标识为false
                        if (CommonConstant.IS_AUTH.equals(name) && Boolean.parseBoolean(values)) {
                            values = Boolean.FALSE.toString();
                        }
                        template.header(name, values);
                    }
                }
            }
        };
    }
}
