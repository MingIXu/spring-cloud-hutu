package com.hutu.cloud.es.properties;

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

	public final static String PREFIX = "spring.elasticsearch";
	/**
	 * 是否开启扫描所有实体类
	 */
	private boolean enableScanForEntities = false;

	/**
	 * 扫描所有实体路径，多个逗号隔开
	 */
	private String basePackages;

	/**
	 * 索引前缀
	 */
	private String indexPrefix;

	/**
	 * 索引后缀
	 */
	private String indexSuffix;

	/**
	 * 是否启用 es 请求日志打印功能
	 */
	private boolean enableLog = false;
}