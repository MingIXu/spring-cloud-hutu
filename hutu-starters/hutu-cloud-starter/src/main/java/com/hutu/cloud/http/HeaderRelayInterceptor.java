package com.hutu.cloud.http;

import com.hutu.cloud.common.util.WebUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 传递 header
 *
 * @author hutu
 * @date 2021/3/9 9:55 上午
 */
public final class HeaderRelayInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = chain.request().newBuilder();
		HttpServletRequest rawRequest = WebUtil.getRequest();
		Enumeration<String> headerNames = Objects.requireNonNull(rawRequest).getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				String value = rawRequest.getHeader(name);
				builder.addHeader(name, value);
			}
		}
		return chain.proceed(builder.build());
	}

}
