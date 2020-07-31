package com.hutu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单
 *
 * @author hutu
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(SecurityProperties.PREFIX)
public class SecurityProperties {
    /**
     * Prefix of {@link SecurityProperties}.
     */
    public static final String PREFIX = "hutu.security";
    /**
     * 放行API集合
     */
    private final List<String> skipUrl = new ArrayList<>();

}
