package com.hutu.cloud.security.interceptor;

import com.hutu.cloud.core.R;
import com.hutu.cloud.core.constant.CommonConstant;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import com.hutu.cloud.core.utils.JsonUtil;
import com.hutu.cloud.security.annotation.PreAuth;
import com.hutu.cloud.security.properties.SecurityProperties;
import com.hutu.cloud.security.utils.ClientUtil;
import com.hutu.cloud.common.util.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * client相关拦截器，此处鉴别是否为合法应用访问，检查客户端信息
 *
 * @author hutu
 * @date 2021/2/3 4:09 下午
 */
@Slf4j
@AllArgsConstructor
public class ClientInterceptor implements HandlerInterceptor {

	final SecurityProperties securityProperties;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.info("====校验客户端拦截器=====");

		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			log.info("not handlerMethod type");
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 注解放行
		PreAuth preAuth = handlerMethod.getMethodAnnotation(PreAuth.class);
		if (preAuth != null && !preAuth.authType().isCheckClient()) {
			log.info("skip client check path: {}", request.getServletPath());
			return true;
		}

		if (ClientUtil.isGatewayClient()) {
			// 内部调用放行
			return true;
		}
		else {
			log.warn("客户端认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request),
					JsonUtil.toJson(request.getParameterMap()));
			R result = R.failed(CommonStatusEnum.UNAUTHORIZED);
			response.setHeader(CommonConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StringPool.UTF_8);
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
			}
			catch (IOException ex) {
				log.error(ex.getMessage());
			}
			return false;
		}
	}

}
