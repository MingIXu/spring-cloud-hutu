package com.hutu.cloud.http;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Loadbalancer RestTemplate
 */
public class LbRestTemplate extends RestTemplate {

	public LbRestTemplate() {
		super();
	}

	public LbRestTemplate(ClientHttpRequestFactory requestFactory) {
		super(requestFactory);
	}

	public LbRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
		super(messageConverters);
	}

}
