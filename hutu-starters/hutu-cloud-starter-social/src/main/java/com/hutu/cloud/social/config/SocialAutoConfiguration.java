package com.hutu.cloud.social.config;

import com.hutu.cloud.social.props.SocialProperties;
import com.xkcoding.http.HttpUtil;
import com.xkcoding.http.support.Http;
import com.xkcoding.http.support.httpclient.HttpClientImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方登录自动配置
 *
 * @author hutu
 * @date 2021/4/2 3:41 下午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SocialProperties.class)
public class SocialAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(Http.class)
	public Http simpleHttp() {
		HttpClientImpl httpClient = new HttpClientImpl();
		HttpUtil.setHttp(httpClient);
		return httpClient;
	}

}
