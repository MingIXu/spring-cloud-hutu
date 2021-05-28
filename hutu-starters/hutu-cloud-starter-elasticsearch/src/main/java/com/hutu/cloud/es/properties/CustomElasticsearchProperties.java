package com.hutu.cloud.es.properties;

import com.hutu.cloud.core.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义的 es 配置
 *
 * @author hutu
 * @date 2020/12/4 9:35 上午
 */
@Setter
@Getter
@ConfigurationProperties(prefix = CustomElasticsearchProperties.PREFIX)
public class CustomElasticsearchProperties {

	final static String PREFIX = "hutu.elasticsearch";

	/**
	 * 是否开启查询偏好 default true
	 */
	boolean enablePreference = true;

	/**
	 * preference
	 */
	String preference = "default_preference";

	String basePackage = CommonConstant.BASE_PACKAGES;

}
