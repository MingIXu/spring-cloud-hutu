package com.hutu.cloud.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单api
 *
 * @author hutu
 */
@Getter
@Setter
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
