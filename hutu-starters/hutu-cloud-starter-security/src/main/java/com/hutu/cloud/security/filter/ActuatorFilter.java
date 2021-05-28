package com.hutu.cloud.security.filter;

import com.hutu.cloud.core.exception.GlobalException;
import com.hutu.cloud.security.utils.ClientUtil;
import com.hutu.cloud.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 专门解决对外暴露的健康检查api安全问题
 *
 * @author hutu
 * @date 2021/2/3 4:08 下午
 */
@Slf4j
@WebFilter(filterName = "actuatorFilter", urlPatterns = "/actuator/*")
public class ActuatorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		log.trace("====健康检查api拦截器=====");
		if (!isMonitor()) {
			throw new GlobalException("不是 monitor 的请求");
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * 支持ip白名单访问与监控中心访问
	 */
	public boolean isMonitor() {
		String ip = WebUtil.getIP();

		boolean ipPass = false;
		for (int i = 0; i < list.size(); i++) {
			if (ip.startsWith(list.get(i))) {
				log.trace("URL is {}, Monitor Client ip is {}", WebUtil.getRequestUri(), ip);
				ipPass = true;
			}
		}

		return ipPass || ClientUtil.isMonitorClient();
	}

	/**
	 * TODO： 监控中心ip地址，此处读取配置
	 */
	final static List<String> list = Arrays.asList("127.0.0.1", "192.168.");

}
