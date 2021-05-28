package com.hutu.cloud.sensitive.converter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.sensitive.annotation.Desensitization;
import com.hutu.cloud.sensitive.format.DesensitizeFormatter;
import com.hutu.cloud.sensitive.serializer.DesensitizationSerializerFactory;
import com.hutu.cloud.sensitive.serializer.ExtraFieldSerializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * jackson 默认值为 null 时的处理
 * <p>
 * 主要是为了避免 app 端出现null导致闪退
 * <p>
 * 规则： number -1 string "" date "" boolean false array [] Object {}
 *
 * @author hutu
 */
public class CustomBeanSerializerModifier extends BeanSerializerModifier {

	private final DesensitizeFormatter desensitizeFormatter;

	public CustomBeanSerializerModifier(DesensitizeFormatter desensitizeFormatter) {
		this.desensitizeFormatter = desensitizeFormatter;
	}

	@Override
	public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc,
			JsonSerializer<?> serializer) {
		if (serializer instanceof BeanSerializerBase) {
			return new ExtraFieldSerializer((BeanSerializerBase) serializer);
		}
		return serializer;
	}

	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		// 循环所有的beanPropertyWriter
		beanProperties.forEach(writer -> {
			// 如果已经有 null 序列化处理如注解：@JsonSerialize(nullsUsing = xxx) 跳过
			if (writer.hasNullSerializer()) {
				return;
			}
			JavaType type = writer.getType();
			Class<?> clazz = type.getRawClass();

			if (type.isTypeOrSubTypeOf(Number.class)) {
				writer.assignNullSerializer(NullJsonSerializers.NUMBER_JSON_SERIALIZER);
			}
			else if (type.isTypeOrSubTypeOf(Boolean.class)) {
				writer.assignNullSerializer(NullJsonSerializers.BOOLEAN_JSON_SERIALIZER);
			}
			else if (type.isTypeOrSubTypeOf(Character.class)) {
				writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
			}
			else if (type.isTypeOrSubTypeOf(String.class)) {
				// 数据脱敏，加解密，数据转换等
				Desensitization annotation = writer.getAnnotation(Desensitization.class);
				if (annotation != null) {
					JsonSerializer<Object> sensitiveSerializer = DesensitizationSerializerFactory
							.getSerializer(annotation, desensitizeFormatter);
					writer.assignSerializer(sensitiveSerializer);
				}
				writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
			}
			else if (type.isArrayType() || clazz.isArray() || type.isTypeOrSubTypeOf(Collection.class)) {
				writer.assignNullSerializer(NullJsonSerializers.ARRAY_JSON_SERIALIZER);
			}
			else if (type.isTypeOrSubTypeOf(OffsetDateTime.class)) {
				writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
			}
			else if (type.isTypeOrSubTypeOf(Date.class) || type.isTypeOrSubTypeOf(TemporalAccessor.class)) {
				writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
			}
			else {
				writer.assignNullSerializer(NullJsonSerializers.OBJECT_JSON_SERIALIZER);
			}
		});
		return super.changeProperties(config, beanDesc, beanProperties);
	}

	public interface NullJsonSerializers {

		JsonSerializer<Object> STRING_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString(StringPool.EMPTY);
			}
		};

		JsonSerializer<Object> NUMBER_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeNumber(StrUtil.INDEX_NOT_FOUND);
			}
		};

		JsonSerializer<Object> BOOLEAN_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeObject(Boolean.FALSE);
			}
		};

		JsonSerializer<Object> ARRAY_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeStartArray();
				gen.writeEndArray();
			}
		};

		JsonSerializer<Object> OBJECT_JSON_SERIALIZER = new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeStartObject();
				gen.writeEndObject();
			}
		};

	}

}
