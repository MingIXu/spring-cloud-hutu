package com.hutu.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 预留控制es的配置
 *
 * @author hutu
 * @date 2020/12/4 9:35 上午
 */
@ConfigurationProperties(prefix = "spring.elasticsearch")
@Setter
@Getter
@ToString
public class ElasticsearchProperties {
    /**
     * 是否开启查询偏好
     * default false
     */
    boolean enablePreference = false;
}
