package com.hutu.sentinel.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.hutu.common.entity.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sentinel配置类
 * @author hutu
 * @date 2019-06-26 9:58
 */
public class SentinelAutoConfigure {
    /**
     * 限流、熔断统一处理类,此处只能处理url为资源名（例如：@GetMapping（"/test"）的情况
     */
    public static class CustomUrlBlockHandler implements BlockExceptionHandler {

        @Override
        public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
            httpServletResponse.getWriter().print(JSON.toJSONString(R.error("flow-limiting")));
        }
    }
}
