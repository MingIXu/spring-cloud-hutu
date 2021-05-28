package com.hutu.cloud.social.props;

import cn.hutool.core.map.MapUtil;
import lombok.Getter;
import lombok.Setter;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 第三方配置
 *
 * @author hutu
 * @date 2021/4/2 3:41 下午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = SocialProperties.PREFIX)
public class SocialProperties {

	final static String PREFIX = "hutu.social";

	/**
	 * 启用
	 */
	private Boolean enabled = false;

	/**
	 * 域名地址
	 */
	private String domain;

	/**
	 * 类型
	 */
	private Map<AuthDefaultSource, AuthConfig> oauth = MapUtil.newHashMap();

	/**
	 * 别名
	 */
	private Map<String, String> alias = MapUtil.newHashMap();

}
