package com.hutu.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hutu.core.constant.CommonConstant;
import com.hutu.core.constant.ProfilesConstant;
import com.hutu.core.constant.StringPool;
import com.hutu.core.exception.GlobalException;
import com.hutu.utils.SpringUtil;
import com.hutu.utils.WebUtil;
import com.hutu.annotation.SkipAuth;
import com.hutu.core.enums.ResultCode;
import com.hutu.core.utils.SignHelper;
import com.hutu.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Token校验与服务间鉴权
 *
 * @author hutu
 * @date 2019/6/6 14:40
 */
@Slf4j
@AllArgsConstructor
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private SecurityProperties securityProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String servletPath = request.getServletPath();

        // 开发环境放行
        if (ProfilesConstant.DEV.equalsIgnoreCase(SpringUtil.getActiveProfile())) {
            log.info("开发环境放行所有请求");
            return true;
        }

        // 白名单放行
        if (isSkip(servletPath)){
            log.info("白名单放行：{}",servletPath);
            return true;
        }

        // 注解放行
        if (handlerMethod.getMethodAnnotation(SkipAuth.class) != null) {
            log.info("该接口为标记跳过鉴权接口: {}",servletPath);
            return true;
        }

        // 判断是否来自网关请求
        String gatewayKey = WebUtil.getRequestParameter(CommonConstant.GATEWAY_KEY);
        if (StrUtil.isEmpty(gatewayKey)||!SignHelper.verify(CommonConstant.GATEWAY_KEY, gatewayKey)){
            throw new GlobalException(ResultCode.NOT_ACCEPTABLE);
        }
        return true;
    }

    /**
     * 处理白名单
     *
     * @param path 请求路径
     * @return boolean
     */
    private boolean isSkip(String path) {
        return securityProperties.getSkipUrl().stream().map(url -> url.replace(StringPool.ASTERISK, StringPool.EMPTY)).anyMatch(path::contains);
    }

}
