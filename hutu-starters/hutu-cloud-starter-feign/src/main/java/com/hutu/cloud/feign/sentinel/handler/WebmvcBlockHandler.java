package com.hutu.cloud.feign.sentinel.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.hutu.cloud.core.R;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import com.hutu.cloud.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * sentinel统一降级策略 支持webmvc模式
 * <p>
 * {@link com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler}
 *
 * @author hutu
 * @date 2020/7/1 2:57 下午
 */
@Slf4j
public class WebmvcBlockHandler implements BlockExceptionHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
		if (e instanceof FlowException) {
			log.error("sentinel 流控降级 资源名称: {}", ((FlowException) e).getRule().toString(), e);
		}
		else if (e instanceof DegradeException) {
			log.error("sentinel 熔断降级 资源名称: {}", ((DegradeException) e).getRule().toString(), e);
		}
		else if (e instanceof SystemBlockException) {
			log.error("sentinel 系统降级 资源名称: {}", ((SystemBlockException) e).getResourceName(), e);
		}
		else if (e instanceof ParamFlowException) {
			log.error("sentinel 热点参数降级 资源名称: {}", ((ParamFlowException) e).getRule().toString(), e);
		}
		else if (e instanceof AuthorityException) {
			log.error("sentinel 鉴权降级 资源名称: {}", ((AuthorityException) e).getRule().toString(), e);
		}
		response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
		R result = R.failed(CommonStatusEnum.SERVICE_IS_BUSY.code,
				StrUtil.isNotEmpty(e.getMessage()) ? e.getMessage() : CommonStatusEnum.SERVICE_IS_BUSY.msg);
		response.setCharacterEncoding(StringPool.UTF_8);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().print(JsonUtil.toJson(result));
	}

}
