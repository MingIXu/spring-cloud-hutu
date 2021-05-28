package com.hutu.cloud.error;

import cn.hutool.core.bean.BeanUtil;
import com.hutu.cloud.core.R;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

/**
 * 全局异常处理
 *
 * @author hutu
 * @date 2021/3/18 9:14 上午
 */
@Slf4j
public class CustomErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

		String requestUri = this.getAttr(webRequest, "javax.servlet.error.request_uri");
		Integer status = this.getAttr(webRequest, "javax.servlet.error.status_code");
		Throwable error = getError(webRequest);
		R result;
		if (status != null && status == CommonStatusEnum.NOT_FOUND.code) {
			log.error("URL:{} error status:{}", requestUri, status);
			result = R.failed(CommonStatusEnum.NOT_FOUND);
		}
		else if (error == null) {
			log.error("URL:{} error status:{}", requestUri, status);
			result = R.failed(CommonStatusEnum.INTERNAL_SERVER_ERROR.code, "系统未知异常[HttpStatus]:" + status);
		}
		else {
			log.error(String.format("URL:%s error status:%d", requestUri, status), error);
			result = R.failed(Objects.requireNonNull(status).intValue(), error.getMessage());
		}
		// TODO 发送服务异常事件
		return BeanUtil.beanToMap(result);
	}

	@Nullable
	private <T> T getAttr(WebRequest webRequest, String name) {
		return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
