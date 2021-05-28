package com.hutu.cloud.sensitive.resolver;

import cn.hutool.json.JSONArray;
import com.hutu.cloud.core.utils.JsonUtil;
import com.hutu.cloud.sensitive.annotation.ArrayRequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 支持解析 hutu 系统传参习惯
 *
 * @author hutu
 * @date 2021/5/19 10:41 上午
 */
@Slf4j
public class ArrayRequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * 设置支持的方法参数类型
	 * @param parameter 方法参数
	 * @return 支持的类型
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 支持带@ArrayRequestBody注解的方法
		return parameter.hasMethodAnnotation(ArrayRequestBody.class);
	}

	/**
	 * 参数解析，利用fastjson 注意：非基本类型返回null会报空指针异常，要通过反射或者JSON工具类创建一个空对象
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		JSONArray arrays = getRequestBody(webRequest);
		Object value = arrays.get(parameter.getParameterIndex());
		// 获取的注解后的类型 Long
		Class<?> parameterType = parameter.getParameterType();
		// 通过注解的value或者参数名解析，能拿到value进行解析
		if (value != null) {
			// 基本类型
			if (parameterType.isPrimitive()) {
				return parsePrimitive(parameterType.getName(), value);
			}
			// 基本类型包装类
			if (isBasicDataTypes(parameterType)) {
				return parseBasicTypeWrapper(parameterType, value);
				// 字符串类型
			}
			else if (parameterType == String.class) {
				return value.toString();
			}
			// 其他复杂对象
			return JsonUtil.parse(value.toString(), parameterType);
		}
		log.debug("parameter is null");
		return null;
	}

	private static final String JSONBODY_ATTRIBUTE = "JSON_REQUEST_BODY";

	/**
	 * 获取请求体JSON字符串
	 */
	private JSONArray getRequestBody(NativeWebRequest webRequest) {
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

		// 有就直接获取
		JSONArray jsonArray = (JSONArray) webRequest.getAttribute(JSONBODY_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
		// 没有就从请求中读取
		if (jsonArray == null) {
			try {
				jsonArray = JsonUtil.parse(Objects.requireNonNull(servletRequest).getInputStream(), JSONArray.class);
				webRequest.setAttribute(JSONBODY_ATTRIBUTE, jsonArray, NativeWebRequest.SCOPE_REQUEST);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return jsonArray;
	}

	/**
	 * 基本类型解析
	 */
	private Object parsePrimitive(String parameterTypeName, Object value) {
		final String booleanTypeName = "boolean";
		if (booleanTypeName.equals(parameterTypeName)) {
			return Boolean.valueOf(value.toString());
		}
		final String intTypeName = "int";
		if (intTypeName.equals(parameterTypeName)) {
			return Integer.valueOf(value.toString());
		}
		final String charTypeName = "char";
		if (charTypeName.equals(parameterTypeName)) {
			return value.toString().charAt(0);
		}
		final String shortTypeName = "short";
		if (shortTypeName.equals(parameterTypeName)) {
			return Short.valueOf(value.toString());
		}
		final String longTypeName = "long";
		if (longTypeName.equals(parameterTypeName)) {
			return Long.valueOf(value.toString());
		}
		final String floatTypeName = "float";
		if (floatTypeName.equals(parameterTypeName)) {
			return Float.valueOf(value.toString());
		}
		final String doubleTypeName = "double";
		if (doubleTypeName.equals(parameterTypeName)) {
			return Double.valueOf(value.toString());
		}
		final String byteTypeName = "byte";
		if (byteTypeName.equals(parameterTypeName)) {
			return Byte.valueOf(value.toString());
		}
		return null;
	}

	/**
	 * 基本类型包装类解析
	 */
	private Object parseBasicTypeWrapper(Class<?> parameterType, Object value) {
		if (Number.class.isAssignableFrom(parameterType)) {
			Number number = (Number) value;
			if (parameterType == Integer.class) {
				return number.intValue();
			}
			else if (parameterType == Short.class) {
				return number.shortValue();
			}
			else if (parameterType == Long.class) {
				return number.longValue();
			}
			else if (parameterType == Float.class) {
				return number.floatValue();
			}
			else if (parameterType == Double.class) {
				return number.doubleValue();
			}
			else if (parameterType == Byte.class) {
				return number.byteValue();
			}
		}
		else if (parameterType == Boolean.class) {
			return value.toString();
		}
		else if (parameterType == Character.class) {
			return value.toString().charAt(0);
		}
		return null;
	}

	/**
	 * 判断是否为基本数据类型包装类
	 */
	private boolean isBasicDataTypes(Class clazz) {
		Set<Class> classSet = new HashSet<>();
		classSet.add(Integer.class);
		classSet.add(Long.class);
		classSet.add(Short.class);
		classSet.add(Float.class);
		classSet.add(Double.class);
		classSet.add(Boolean.class);
		classSet.add(Byte.class);
		classSet.add(Character.class);
		return classSet.contains(clazz);
	}

}
