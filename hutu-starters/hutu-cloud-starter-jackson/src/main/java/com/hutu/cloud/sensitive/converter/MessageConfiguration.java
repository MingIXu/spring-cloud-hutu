package com.hutu.cloud.sensitive.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hutu.cloud.core.utils.Charsets;
import com.hutu.cloud.sensitive.format.DesensitizeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 消息配置类
 *
 * @author hutu
 * @date 2021/4/8 10:43 上午
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MessageConfiguration implements WebMvcConfigurer {

	private final ObjectMapper objectMapper;

	private final DesensitizeFormatter desensitizeFormatter;

	/**
	 * 使用 JACKSON 作为JSON MessageConverter
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.removeIf(
				x -> x instanceof StringHttpMessageConverter || x instanceof AbstractJackson2HttpMessageConverter);
		converters.add(new StringHttpMessageConverter(Charsets.UTF_8));
		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new ResourceRegionHttpMessageConverter());
		converters.add(new MappingApiJackson2HttpMessageConverter(objectMapper, desensitizeFormatter));
	}

}
