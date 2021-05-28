package com.hutu.cloud.gateway.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 *
 * @author hutu
 */
@Getter
@Setter
@ConfigurationProperties(AuthProperties.PREFIX)
public class AuthProperties {

	/**
	 * Prefix of {@link AuthProperties}.
	 */
	public static final String PREFIX = "hutu.security";

	public String appSecret;

	/**
	 * 放行API集合
	 */
	private final List<String> skipUrl = new ArrayList<>();

}
