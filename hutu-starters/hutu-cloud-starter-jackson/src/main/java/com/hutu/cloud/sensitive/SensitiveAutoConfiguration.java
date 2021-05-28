package com.hutu.cloud.sensitive;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hutu.cloud.core.jackson.JavaTimeModule;
import com.hutu.cloud.core.utils.DateUtils;
import com.hutu.cloud.sensitive.auth.DefaultSensitiveAuthServiceImpl;
import com.hutu.cloud.sensitive.auth.SensitiveAuthService;
import com.hutu.cloud.sensitive.converter.MessageConfiguration;
import com.hutu.cloud.sensitive.format.DefaultDesensitizeFormatter;
import com.hutu.cloud.sensitive.format.DesensitizeFormatter;
import com.hutu.cloud.sensitive.properties.DesensitizeProperties;
import com.hutu.cloud.sensitive.resolver.ArrayRequestBodyArgumentResolver;
import com.hutu.cloud.sensitive.serializer.DesensitizationSerializerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 敏感插件自动配置类
 *
 * @author hutu
 * @date 2021/4/7 4:09 下午
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@Import({ MessageConfiguration.class })
@EnableConfigurationProperties({ DesensitizeProperties.class })
public class SensitiveAutoConfiguration implements WebMvcConfigurer {

	@Bean
	@ConditionalOnMissingBean
	public DesensitizeFormatter desensitize(DesensitizeProperties desensitizeProperties) {
		return new DefaultDesensitizeFormatter(desensitizeProperties);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// 添加@ArrayRequestBody注解的解析器
		argumentResolvers.add(new ArrayRequestBodyArgumentResolver());
	}

	@Bean
	@ConditionalOnMissingBean
	public SensitiveAuthService sensitiveAuthService() {
		return new DefaultSensitiveAuthServiceImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public DesensitizationSerializerFactory desensitizationSerializerFactory(
			SensitiveAuthService sensitiveAuthService) {
		return new DesensitizationSerializerFactory(sensitiveAuthService);
	}

	@Primary
	@Bean
	public ObjectMapper objectMapper() {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
		builder.simpleDateFormat(DateUtils.PATTERN_DATETIME);
		// 处理long返回前端长度溢出问题
		builder.serializerByType(Long.class, ToStringSerializer.instance);
		// 创建ObjectMapper
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		// 设置地点为中国
		objectMapper.setLocale(Locale.CHINA);
		// 去掉默认的时间戳格式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 设置为中国上海时区
		objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
		// 序列化时，日期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat(DateUtils.PATTERN_DATETIME, Locale.CHINA));
		// 序列化处理
		objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
		objectMapper.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
		objectMapper.findAndRegisterModules();
		// 失败处理
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 单引号处理
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 反序列化时，属性不存在的兼容处理
		objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 日期格式化
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.findAndRegisterModules();
		return objectMapper;
	}

}
