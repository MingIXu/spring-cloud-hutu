package com.hutu.cloud.props;

import com.hutu.cloud.http.HttpLoggingInterceptor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.hutu.cloud.http.HttpLoggingInterceptor.Level.BODY;

/**
 * http-client 熟悉设置
 *
 * @author hutu
 * @date 2021/3/9 10:12 上午
 */
@Getter
@Setter
@ConfigurationProperties(HttpClientProperties.PREFIX)
public class HttpClientProperties {

	/**
	 * Prefix of {@link HttpClientProperties}.
	 */
	public static final String PREFIX = "hutu.http-client";

	HttpLoggingInterceptor.Level level = BODY;

	int readTime = 30;

	int writeTime = 30;

}
