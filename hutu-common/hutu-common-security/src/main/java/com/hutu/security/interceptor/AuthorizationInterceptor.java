package com.hutu.security.interceptor;

import com.hutu.common.utils.token.TokenUtil;
import com.hutu.security.annotation.SkipAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;

import static com.hutu.security.constant.SecureConstant.WHITE_WORDS;

/**
 * 权限(Token)验证
 *
 * @author hutu
 * @date 2019/6/6 14:40
 */
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        SkipAuth skipAuth;
        if (handler instanceof HandlerMethod) {
            skipAuth = ((HandlerMethod) handler).getMethodAnnotation(SkipAuth.class);
            //如果有@SkipAuth注解或白名单，则不验证token。判断是否有token且是合法的没过期的
            return isWhilePath(request.getServletPath()) || skipAuth != null || TokenUtil.validateToken();
        } else {
            return true;
        }
    }

    private boolean isWhilePath(String servletPath) {
        HashSet<String> whitePaths = new HashSet<>(Arrays.asList(WHITE_WORDS));
        for (String path : whitePaths) {
            if (servletPath.contains(path) || "".equals(servletPath) || "/".equals(servletPath)) {
                return true;
            }
        }
        return false;
    }


}
