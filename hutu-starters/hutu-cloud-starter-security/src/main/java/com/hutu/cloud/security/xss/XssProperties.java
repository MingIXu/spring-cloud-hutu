package com.hutu.cloud.security.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Xss配置类
 */
@Data
@ConfigurationProperties(XssProperties.PREFIX)
public class XssProperties {

	public static final String PREFIX = "hutu.security.xss";

	/**
	 * 开启xss
	 */
	private Boolean enabled = true;

	/**
	 * 放行url
	 */
	private List<String> skipUrl = new ArrayList<>();

}
