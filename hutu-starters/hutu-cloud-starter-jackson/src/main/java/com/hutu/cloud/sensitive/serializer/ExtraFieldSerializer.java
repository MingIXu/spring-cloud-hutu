package com.hutu.cloud.sensitive.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.hutu.cloud.core.utils.AnnotationUtils;
import com.hutu.cloud.core.utils.ReflectionUtils;
import com.hutu.cloud.sensitive.annotation.Dictionary;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * 自定义 BeanSerializerBase 主要增加字典的 Text 扩展属性 例如：sex = 1 ，增加 sexText = 男属性
 *
 * @author hutu
 * @date 2021/4/8 5:23 下午
 */
public class ExtraFieldSerializer extends BeanSerializerBase {

	public ExtraFieldSerializer(BeanSerializerBase source) {
		super(source);
	}

	ExtraFieldSerializer(ExtraFieldSerializer source, ObjectIdWriter objectIdWriter) {
		super(source, objectIdWriter);
	}

	ExtraFieldSerializer(ExtraFieldSerializer source, Set<String> toIgnore) {
		super(source, toIgnore);
	}

	@Override
	public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
		return new ExtraFieldSerializer(this, objectIdWriter);
	}

	@Override
	protected BeanSerializerBase withIgnorals(Set<String> toIgnore) {
		return new ExtraFieldSerializer(this, toIgnore);
	}

	@Override
	protected BeanSerializerBase asArraySerializer() {
		return null;
	}

	@Override
	public BeanSerializerBase withFilterId(Object filterId) {
		return null;
	}

	@Override
	public void serialize(Object bean, JsonGenerator jen, SerializerProvider provider) throws IOException {
		jen.writeStartObject();
		serializeFields(bean, jen, provider);
		Field[] fields = ReflectionUtils.getAllFields(bean);
		for (Field field : fields) {
			Dictionary annotation = AnnotationUtils.getAnnotation(field, Dictionary.class);
			if (annotation != null) {
				// TODO 此处可添加获取字典逻辑
				jen.writeStringField(field.getName() + "Text", annotation.value());
			}
		}
		jen.writeEndObject();
	}

}