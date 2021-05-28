package com.hutu.cloud.feign.contract;

import com.hutu.cloud.feign.annotation.CustomFeignClient;
import com.hutu.cloud.feign.enums.RpcType;
import feign.MethodMetadata;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.convert.ConversionService;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class CustomSpringMvcContract extends SpringMvcContract {

	public CustomSpringMvcContract(List<AnnotatedParameterProcessor> parameterProcessors,
			ConversionService feignConversionService, boolean decodeSlash) {
		super(parameterProcessors, feignConversionService, decodeSlash);
	}

	@Override
	public List<MethodMetadata> parseAndValidateMetadata(Class<?> targetType) {
		CustomFeignClient customFeignClient = targetType.getAnnotation(CustomFeignClient.class);
		if (customFeignClient != null && RpcType.TCP == customFeignClient.rpcType()) {
			return new ArrayList<>();
		}
		return super.parseAndValidateMetadata(targetType);
	}

	/**
	 * 适配 hutu 传递数组参数问题
	 * @param data
	 * @param annotations
	 * @param paramIndex
	 * @return
	 */
	@Override
	protected boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
		super.processAnnotationsOnParameter(data, annotations, paramIndex);
		CustomFeignClient annotation = data.targetType().getAnnotation(CustomFeignClient.class);
		if (annotation != null && paramIndex == 0) {
			return false;
		}
		return true;
	}

}
