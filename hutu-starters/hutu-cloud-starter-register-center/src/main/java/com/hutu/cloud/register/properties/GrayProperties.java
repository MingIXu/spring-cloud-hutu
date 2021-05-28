package com.hutu.cloud.register.properties;

import com.hutu.cloud.core.constant.ProjectConstant;
import com.hutu.cloud.core.constant.StringPool;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 灰度发布配置类
 *
 * @author hutu
 * @date 2021/4/20 5:48 下午
 */
@Setter
@Getter
@ConfigurationProperties(prefix = GrayProperties.PREFIX)
public class GrayProperties {

	final static String PREFIX = ProjectConstant.PROJECT_NAME + StringPool.DOT + "gray";

	boolean enable = false;

	String version = "1.0.1";

	String zone;

	String region;

	String group;

	String env;

}
