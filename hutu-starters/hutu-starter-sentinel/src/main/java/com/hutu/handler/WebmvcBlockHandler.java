package com.hutu.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hutu.core.R;
import com.hutu.core.constant.StringPool;
import com.hutu.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * sentinel统一降级限流策略 支持webmvc模式
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
		log.error("sentinel 降级 资源名称: {}", e.getRule().getResource(), e);

		response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
		R result = R.error(ResultCode.SERVICE_IS_BUSY.code, StrUtil.isNotEmpty(e.getMessage())?e.getMessage():ResultCode.SERVICE_IS_BUSY.msg);
		response.setCharacterEncoding(StringPool.UTF_8);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().print(JSONUtil.toJsonStr(result));
	}

}
