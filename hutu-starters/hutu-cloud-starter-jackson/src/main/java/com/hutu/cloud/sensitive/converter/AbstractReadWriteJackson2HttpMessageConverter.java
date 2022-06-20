package com.hutu.cloud.sensitive.converter;

import cn.hutool.core.util.CharsetUtil;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.util.TypeUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 分读写的 json 消息 处理器
 *
 * @author hutu
 * @date 2021/4/8 4:46 下午
 */
public abstract class AbstractReadWriteJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

	private static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;

	private ObjectMapper writeObjectMapper;

	@Nullable
	private PrettyPrinter ssePrettyPrinter;

	public AbstractReadWriteJackson2HttpMessageConverter(ObjectMapper readObjectMapper,
			ObjectMapper writeObjectMapper) {
		super(readObjectMapper);
		this.writeObjectMapper = writeObjectMapper;
		initSsePrettyPrinter();
	}

	public AbstractReadWriteJackson2HttpMessageConverter(ObjectMapper readObjectMapper, ObjectMapper writeObjectMapper,
			MediaType supportedMediaType) {
		this(readObjectMapper, writeObjectMapper);
		setSupportedMediaTypes(Collections.singletonList(supportedMediaType));
		initSsePrettyPrinter();
	}

	public AbstractReadWriteJackson2HttpMessageConverter(ObjectMapper readObjectMapper, ObjectMapper writeObjectMapper,
			MediaType... supportedMediaTypes) {
		this(readObjectMapper, writeObjectMapper);
		setSupportedMediaTypes(Arrays.asList(supportedMediaTypes));
	}

	private void initSsePrettyPrinter() {
		setDefaultCharset(DEFAULT_CHARSET);
		DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
		prettyPrinter.indentObjectsWith(new DefaultIndenter("  ", "\ndata:"));
		this.ssePrettyPrinter = prettyPrinter;
	}

	@Override
	public boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType) {
		if (!canWrite(mediaType)) {
			return false;
		}
		AtomicReference<Throwable> causeRef = new AtomicReference<>();
		if (this.defaultObjectMapper.canSerialize(clazz, causeRef)) {
			return true;
		}
		logWarningIfNecessary(clazz, causeRef.get());
		return false;
	}

	@Override
	protected void writeInternal(Object object, @Nullable Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		MediaType contentType = outputMessage.getHeaders().getContentType();
		JsonEncoding encoding = getJsonEncoding(contentType);

		OutputStream outputStream = StreamUtils.nonClosing(outputMessage.getBody());
		try (JsonGenerator generator = this.writeObjectMapper.getFactory().createGenerator(outputStream, encoding)) {
			writePrefix(generator, object);

			Object value = object;
			Class<?> serializationView = null;
			FilterProvider filters = null;
			JavaType javaType = null;

			if (object instanceof MappingJacksonValue) {
				MappingJacksonValue container = (MappingJacksonValue) object;
				value = container.getValue();
				serializationView = container.getSerializationView();
				filters = container.getFilters();
			}
			if (type != null && TypeUtils.isAssignable(type, value.getClass())) {
				javaType = getJavaType(type, null);
			}

			ObjectWriter objectWriter = (serializationView != null
					? this.writeObjectMapper.writerWithView(serializationView) : this.writeObjectMapper.writer());
			if (filters != null) {
				objectWriter = objectWriter.with(filters);
			}
			if (javaType != null && javaType.isContainerType()) {
				objectWriter = objectWriter.forType(javaType);
			}
			SerializationConfig config = objectWriter.getConfig();
			if (contentType != null && contentType.isCompatibleWith(MediaType.TEXT_EVENT_STREAM)
					&& config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
				objectWriter = objectWriter.with(this.ssePrettyPrinter);
			}
			objectWriter.writeValue(generator, value);

			writeSuffix(generator, object);
			generator.flush();
		}
		catch (InvalidDefinitionException ex) {
			throw new HttpMessageConversionException("Type definition error: " + ex.getType(), ex);
		}
		catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getOriginalMessage(), ex);
		}
	}

}
