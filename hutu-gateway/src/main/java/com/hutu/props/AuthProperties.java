package com.hutu.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 *
 * @author hutu
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(AuthProperties.PREFIX)
public class AuthProperties {
	/**
	 * Prefix of {@link AuthProperties}.
	 */
	public static final String PREFIX = "hutu.security";

	/**
	 * 放行API集合
	 */
	private final List<String> skipUrl = new ArrayList<>();

}
