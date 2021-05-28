package com.hutu.cloud.feign;

import com.hutu.cloud.common.util.WebUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * feign 传递Request header
 *
 * @author hutu
 * @date 2021/3/5 3:58 下午
 */
public class CustomFeignRequestHeaderInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		HttpServletRequest request = WebUtil.getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				String values = request.getHeader(name);
				requestTemplate.header(name, values);
			}
		}
	}

}