package com.hutu.cloud.sensitive.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.TreeMap;

/**
 * 脱敏配置类
 *
 * @author hutu
 * @date 2021/4/8 2:36 下午
 */
@ConfigurationProperties(prefix = DesensitizeProperties.PREFIX)
public class DesensitizeProperties {

	final static String PREFIX = "hutu.sensitive.desensitize";

	boolean enable = false;

	private Map<String, RuleProperty> rules;

	public DesensitizeProperties() {
		rules = new TreeMap<>();
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Map<String, RuleProperty> getRules() {
		return rules;
	}

	public void setRules(Map<String, RuleProperty> rules) {
		this.rules.putAll(rules);
	}

}
