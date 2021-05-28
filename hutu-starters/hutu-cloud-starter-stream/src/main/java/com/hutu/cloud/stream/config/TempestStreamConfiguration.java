package com.hutu.cloud.stream.config;

import com.hutu.cloud.stream.converter.MappingHessianConverter;
import com.hutu.cloud.stream.function.TempestStreamBridge;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class TempestStreamConfiguration {

	@Bean
	public MappingHessianConverter mappingHessianConverter() {
		return new MappingHessianConverter();
	}

	@Bean
	public TempestStreamBridge hutuStreamBridge(StreamBridge streamBridge) {
		return new TempestStreamBridge(streamBridge);
	}

}
