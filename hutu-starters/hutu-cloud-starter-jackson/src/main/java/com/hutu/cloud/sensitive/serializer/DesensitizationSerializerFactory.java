package com.hutu.cloud.sensitive.serializer;

import cn.hutool.core.lang.Singleton;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.hutu.cloud.sensitive.annotation.Desensitization;
import com.hutu.cloud.sensitive.auth.SensitiveAuthService;
import com.hutu.cloud.sensitive.format.DesensitizeFormatter;
import com.hutu.cloud.sensitive.serializer.jackson.DesensitizationSerializer;

/**
 * 脱敏序列化工厂
 *
 * @author hutu
 * @date 2021/4/8 1:49 下午
 */
public class DesensitizationSerializerFactory {

	static SensitiveAuthService authService;

	public DesensitizationSerializerFactory(SensitiveAuthService authService) {
		DesensitizationSerializerFactory.authService = authService;
	}

	public static JsonSerializer<Object> getSerializer(Desensitization desensitization,
			DesensitizeFormatter desensitizeFormatter) {
		DesensitizationSerializer desensitizationSerializer = Singleton.get(DesensitizationSerializer.class,
				desensitization, desensitizeFormatter);
		desensitizationSerializer.setSensitiveAuthService(authService);
		return desensitizationSerializer;
	}

}
