package com.hutu.cloud.security.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Xss配置类
 */
@Data
@ConfigurationProperties(XssUrlProperties.PREFIX)
public class XssUrlProperties {

	public static final String PREFIX = "hutu.security.url";

	private final List<String> excludePatterns = new ArrayList<>();

}
