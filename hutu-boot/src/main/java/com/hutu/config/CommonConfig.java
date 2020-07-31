package com.hutu.config;

import com.hutu.utils.SpringUtil;
import com.hutu.handler.CustomRestNotFoundHandler;
import com.hutu.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 引入controller全局异常处理和自定义404处理
 * <p>
 * ErrorMvcAutoConfiguration 会先配置 BasicErrorController导致 404 mapping重复
 * 此处在 ErrorMvcAutoConfiguration 之前配置会导致 BasicErrorController 不装载。
 * 详情看 BasicErrorController 装载条件。
 *
 * @author hutu
 * @date 2019-12-11 16:17
 */
@Configuration
@Import({CustomRestNotFoundHandler.class, GlobalExceptionHandler.class})
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class CommonConfig {
    /**
     * Spring上下文缓存, 此处初始化springUtil静态类中的context
     *
     * @return SpringUtil
     */
    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }
}
