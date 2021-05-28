package com.hutu.cloud.security.interceptor;

import com.hutu.cloud.core.constant.CommonConstant;
import com.hutu.cloud.security.annotation.PreAuth;
import com.hutu.cloud.core.R;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import com.hutu.cloud.core.utils.JsonUtil;
import com.hutu.cloud.token.util.TokenUtil;
import com.hutu.cloud.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token校验与服务间鉴权
 *
 * @author hutu
 * @date 2021/2/3 4:09 下午
 */
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.info("====登录校验拦截器=====");
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		String servletPath = request.getServletPath();

		// 注解放行
		PreAuth preAuth = handlerMethod.getMethodAnnotation(PreAuth.class);
		if (preAuth != null && !preAuth.authType().isCheckLogin()) {
			log.info("skip login check path: {}", servletPath);
			return true;
		}
		// 已登录放行
		else if (TokenUtil.isLogin()) {
			log.info("login check pass, path: {}", servletPath);
			return true;
		}
		else {
			log.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}", request.getRequestURI(), WebUtil.getIP(request),
					JsonUtil.toJsonString(request.getParameterMap()));
			R result = R.failed(CommonStatusEnum.UNAUTHORIZED);
			response.setCharacterEncoding(StringPool.UTF_8);
			response.setHeader(CommonConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				response.getWriter().write(Objects.requireNonNull(JsonUtil.toJsonString(result)));
			}
			catch (IOException ex) {
				log.error(ex.getMessage());
			}
			return false;
		}
	}

}
