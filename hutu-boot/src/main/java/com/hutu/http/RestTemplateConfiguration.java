package com.hutu.http;


import com.hutu.core.constant.ProfilesConstant;
import com.hutu.core.utils.Charsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.commons.httpclient.OkHttpClientConnectionPoolFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RestTemplate 配置
 *
 * @author hutu
 * @date 2020/5/26 5:14 下午
 */
@Configuration
@ConditionalOnClass(okhttp3.OkHttpClient.class)
@AllArgsConstructor
public class RestTemplateConfiguration {
	private final ObjectMapper objectMapper;

	/**
	 * prod 环境只打印请求url
	 * @return HttpLoggingInterceptor
	 */
	@Bean("httpLoggingInterceptor")
	@Profile(ProfilesConstant.PROD)
	public HttpLoggingInterceptor prodLoggingInterceptor() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new OkHttpSlf4jLogger());
		interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
		return interceptor;
	}

	/**
	 * 除了开发环境打印出BODY
	 * @return HttpLoggingInterceptor
	 */
	@Bean("httpLoggingInterceptor")
	@ConditionalOnMissingBean(HttpLoggingInterceptor.class)
	public HttpLoggingInterceptor testLoggingInterceptor() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new OkHttpSlf4jLogger());
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return interceptor;
	}

	/**
	 * okhttp3 链接池配置
	 * @param connectionPoolFactory 链接池配置
	 * @param httpClientProperties httpClient配置
	 * @return okhttp3.ConnectionPool
	 */
	@Bean
	@ConditionalOnMissingBean(okhttp3.ConnectionPool.class)
	public okhttp3.ConnectionPool httpClientConnectionPool(
		FeignHttpClientProperties httpClientProperties,
		OkHttpClientConnectionPoolFactory connectionPoolFactory) {
		Integer maxTotalConnections = httpClientProperties.getMaxConnections();
		Long timeToLive = httpClientProperties.getTimeToLive();
		TimeUnit ttlUnit = httpClientProperties.getTimeToLiveUnit();
		return connectionPoolFactory.create(maxTotalConnections, timeToLive, ttlUnit);
	}

	/**
	 * 配置OkHttpClient
	 * @param httpClientFactory httpClient 工厂
	 * @param connectionPool 链接池配置
	 * @param httpClientProperties httpClient配置
	 * @param interceptor 拦截器
	 * @return OkHttpClient
	 */
	@Bean
	@ConditionalOnMissingBean(okhttp3.OkHttpClient.class)
	public okhttp3.OkHttpClient httpClient(
		OkHttpClientFactory httpClientFactory,
		okhttp3.ConnectionPool connectionPool,
		FeignHttpClientProperties httpClientProperties,
		HttpLoggingInterceptor interceptor) {
		Boolean followRedirects = httpClientProperties.isFollowRedirects();
		Integer connectTimeout = httpClientProperties.getConnectionTimeout();
		return httpClientFactory.createBuilder(httpClientProperties.isDisableSslValidation())
			.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.followRedirects(followRedirects)
			.connectionPool(connectionPool)
			.addInterceptor(interceptor)
			.build();
	}

	/**
	 * 普通的 RestTemplate，不透传请求头，一般只做外部 http 调用
	 * @param httpClient OkHttpClient
	 * @return RestTemplate
	 */
	@Bean
	@ConditionalOnMissingBean(RestTemplate.class)
	public RestTemplate restTemplate(okhttp3.OkHttpClient httpClient) {
		RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(httpClient));
		configMessageConverters(restTemplate.getMessageConverters());
		return restTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(CustomRestTemplate.class)
	public CustomRestTemplate ssdevRestTemplate(okhttp3.OkHttpClient httpClient) {
		CustomRestTemplate restTemplate = new CustomRestTemplate(new OkHttp3ClientHttpRequestFactory(httpClient));
		configMessageConverters(restTemplate.getMessageConverters());
		return restTemplate;
	}

	/**
	 * 支持负载均衡的 LbRestTemplate
	 * @param httpClient OkHttpClient
	 * @return LbRestTemplate
	 */
	@Bean
	@LoadBalanced
	@ConditionalOnMissingBean(LbRestTemplate.class)
	public LbRestTemplate lbRestTemplate(okhttp3.OkHttpClient httpClient) {
		LbRestTemplate lbRestTemplate = new LbRestTemplate(new OkHttp3ClientHttpRequestFactory(httpClient));
		configMessageConverters(lbRestTemplate.getMessageConverters());
		return lbRestTemplate;
	}

	private void configMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.removeIf(x -> x instanceof StringHttpMessageConverter || x instanceof MappingJackson2HttpMessageConverter);
		converters.add(new StringHttpMessageConverter(Charsets.UTF_8));
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
	}
}
