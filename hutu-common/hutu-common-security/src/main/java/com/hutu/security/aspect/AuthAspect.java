package com.hutu.security.aspect;

import com.hutu.common.enums.ResultCode;
import com.hutu.common.exception.GlobalException;
import com.hutu.security.annotation.PreAuth;
import com.hutu.security.service.AuthService;
import com.hutu.security.service.DefaultAuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 权限校验切片
 *
 * @author hutu
 * @date 2019/6/19 17:51
 */
@Slf4j
@Aspect
public class AuthAspect {

    @Autowired(required = false)
    AuthService authService;

    @Pointcut("@annotation(com.hutu.security.annotation.PreAuth)")
    public void permissionsPointCut() {
    }

    @Before("permissionsPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PreAuth preAuth = method.getAnnotation(PreAuth.class);
        if (authService == null) {
            authService = new DefaultAuthServiceImpl();
        }
        if (!authService.doAuth(preAuth.logical(), preAuth.value())) {
            log.info("无权限访问,需要权限：" + Arrays.toString(preAuth.value()));
            throw new GlobalException(ResultCode.UNAUTHORIZED);
        }
    }
}
