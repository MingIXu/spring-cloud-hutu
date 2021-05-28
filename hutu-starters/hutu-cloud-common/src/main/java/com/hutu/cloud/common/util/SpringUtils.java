package com.hutu.cloud.common.util;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * spring 上下文工具类
 *
 * @author hutu
 * @date 2020-01-10 17:16
 */
@Slf4j
public class SpringUtils extends SpringUtil {

	public static void publishEvent(ApplicationEvent event) {
		try {
			getApplicationContext().publishEvent(event);
		}
		catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	/**
	 * 获取该服务的所有对外暴露的url列表
	 */
	public static List<String> getAllUrl() {
		RequestMappingHandlerMapping mapping = getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
		List<String> urls = new ArrayList<>();
		map.keySet().forEach(info -> {
			Set<String> patterns = Objects.requireNonNull(info.getPatternsCondition()).getPatterns();
			for (String url : patterns) {
				// 排除不要统计的
				if ("/error".equals(url) || "/getAllUrl".equals(url)) {
					continue;
				}
				urls.add(url);
			}
		});
		return urls;
	}

}
