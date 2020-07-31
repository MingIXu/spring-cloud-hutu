package com.hutu.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 自动填充配置
 *
 * @author hutu
 * @date 2020/7/10 10:37 上午
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = MybatisPlusAutoFillProperties.AUTO_FILL_PREFIX)
@RefreshScope
public class MybatisPlusAutoFillProperties {
    public final static String AUTO_FILL_PREFIX = "hutu.mybatis-plus.auto-fill";
    /**
     * 是否开启自动填充字段
     */
    private Boolean enabled = true;
    /**
     * 是否开启了插入填充
     */
    private Boolean enableInsertFill = true;
    /**
     * 是否开启了更新填充
     */
    private Boolean enableUpdateFill = true;

    /**
     * 创建时间字段名
     */
    private String createTimeField = "createTime";
    private String createUserIdField = "createId";
    private String createUserNameField = "createName";
    /**
     * 更新时间字段名
     */
    private String updateTimeField = "updateTime";
    private String updateUserIdField = "updateId";
    private String updateUserNameField = "updateName";

    private String tenantId = "tenantId";
}
