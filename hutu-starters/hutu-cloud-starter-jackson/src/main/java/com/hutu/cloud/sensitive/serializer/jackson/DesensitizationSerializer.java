package com.hutu.cloud.sensitive.serializer.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hutu.cloud.sensitive.annotation.Desensitization;
import com.hutu.cloud.sensitive.auth.SensitiveAuthService;
import com.hutu.cloud.sensitive.format.DesensitizeFormatter;
import lombok.ToString;

import java.io.IOException;

/**
 * jackson 自定义上下文序列化
 *
 * @author hutu
 * @date 2021/4/7 3:14 下午
 */
@ToString
public class DesensitizationSerializer extends JsonSerializer<Object> {

	private final Desensitization desensitization;

	private final DesensitizeFormatter desensitizeFormatter;

	private SensitiveAuthService sensitiveAuthService;

	public void setSensitiveAuthService(SensitiveAuthService sensitiveAuthService) {
		this.sensitiveAuthService = sensitiveAuthService;
	}

	public DesensitizationSerializer(Desensitization desensitization, DesensitizeFormatter desensitizeFormatter) {
		this.desensitization = desensitization;
		this.desensitizeFormatter = desensitizeFormatter;
	}

	@Override
	public void serialize(Object s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {

		if (sensitiveAuthService.skipDesensitization()) {
			jsonGenerator.writeString((String) s);
		}
		else {
			jsonGenerator.writeString(desensitizeFormatter.format((String) s, desensitization));
		}
	}

}