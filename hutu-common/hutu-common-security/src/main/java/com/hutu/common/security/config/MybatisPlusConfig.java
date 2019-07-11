package com.hutu.common.security.config;

import com.hutu.common.db.config.DefaultMybatisPlusConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * mybatisPlus配置
 *
 * @author hutu
 * @date 2019/6/19 18:35
 */
@Import(InitMetaObjectHandler.class)
@Configuration
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {

}
