package com.hutu.db.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * mybatis-plus默认配置
 *
 * @author hutu
 * @date 2019/6/21 9:23
 */
@Configuration
@Import(InitMetaObjectHandler.class)
@MapperScan("com.hutu.**.mapper.**")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}