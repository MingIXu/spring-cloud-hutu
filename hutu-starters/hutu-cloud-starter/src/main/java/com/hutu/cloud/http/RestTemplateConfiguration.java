package com.hutu.cloud.http;

import cn.hutool.core.util.CharsetUtil;
import com.hutu.cloud.core.constant.ProfilesConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hutu.cloud.props.FeignHeadersProperties;
import com.hutu.cloud.props.HttpClientProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({ FeignHeadersProperties.class, HttpClientProperties.class })
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(okhttp3.OkHttpClient.class)
@AllArgsConstructor
public class RestTemplateConfiguration {

	private final ObjectMapper objectMapper;

	private final FeignHeadersProperties feignHeadersProperties;

	private final HttpClientProperties httpClientProperties;

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
	 * 除了 prod 环境打印出BODY或自定义
	 * @return HttpLoggingInterceptor
	 */
	@Bean("httpLoggingInterceptor")
	@ConditionalOnMissingBean(HttpLoggingInterceptor.class)
	public HttpLoggingInterceptor testLoggingInterceptor() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new OkHttpSlf4jLogger());
		interceptor.setLevel(httpClientProperties.getLevel());
		return interceptor;
	}

	@Bean
	public HeaderRelayInterceptor headerRelayInterceptor() {
		return new HeaderRelayInterceptor();
	}

	/**
	 * okhttp3 链接池配置
	 * @param connectionPoolFactory 链接池配置
	 * @param httpClientProperties httpClient配置
	 * @return okhttp3.ConnectionPool
	 */
	@Bean
	@ConditionalOnMissingBean(okhttp3.ConnectionPool.class)
	public okhttp3.ConnectionPool httpClientConnectionPool(FeignHttpClientProperties httpClientProperties,
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
	 * @param logInterceptor 拦截器
	 * @return OkHttpClient
	 */
	@Bean
	@ConditionalOnMissingBean(okhttp3.OkHttpClient.class)
	public okhttp3.OkHttpClient httpClient(OkHttpClientFactory httpClientFactory, okhttp3.ConnectionPool connectionPool,
			FeignHttpClientProperties httpClientProperties, HttpLoggingInterceptor logInterceptor,
			HeaderRelayInterceptor relayInterceptor) {
		boolean followRedirects = httpClientProperties.isFollowRedirects();
		int connectTimeout = httpClientProperties.getConnectionTimeout();
		return httpClientFactory.createBuilder(httpClientProperties.isDisableSslValidation())
				.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
				.writeTimeout(this.httpClientProperties.getWriteTime(), TimeUnit.SECONDS)
				.readTimeout(this.httpClientProperties.getReadTime(), TimeUnit.SECONDS).followRedirects(followRedirects)
				.connectionPool(connectionPool).addInterceptor(relayInterceptor).addInterceptor(logInterceptor).build();
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
		converters.removeIf(
				x -> x instanceof StringHttpMessageConverter || x instanceof MappingJackson2HttpMessageConverter);
		converters.add(new StringHttpMessageConverter(CharsetUtil.CHARSET_UTF_8));
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
	}

}
