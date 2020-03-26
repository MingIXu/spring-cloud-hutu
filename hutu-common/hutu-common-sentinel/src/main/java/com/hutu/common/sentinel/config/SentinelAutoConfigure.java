package com.hutu.common.sentinel.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.hutu.common.core.entity.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sentinel配置类
 * @author hutu
 * @date 2019-06-26 9:58
 */
public class SentinelAutoConfigure {
    public SentinelAutoConfigure() {
        WebCallbackManager.setUrlBlockHandler(new CustomUrlBlockHandler());
    }

    /**
     * 限流、熔断统一处理类
     */
    public class CustomUrlBlockHandler implements UrlBlockHandler {
        @Override
        public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
            httpServletResponse.getWriter().print(JSON.toJSONString(R.error("flow-limiting")));
        }
    }
}
