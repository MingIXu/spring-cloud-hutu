package com.hutu.common.security.aspect;

import com.hutu.auth.enums.ErrorMsgEnum;
import com.hutu.auth.exception.GlobalException;
import com.hutu.common.security.annotation.Logical;
import com.hutu.common.security.annotation.RequiresPermissions;
import com.hutu.common.security.service.HutuPermissionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 权限校验切片
 *
 * @author hutu
 * @date 2019/6/19 17:51
 */
@Aspect
@Component
public class PermissionsAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = false)
	HutuPermissionService hutuPermissionService;

	@Pointcut("@annotation(com.hutu.common.security.annotation.RequiresPermissions)")
	public void permissionsPointCut() {}

	@Before("permissionsPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		logger.info("---此处权限校验---");
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		if (hutuPermissionService == null) {
			logger.info("没有实现获取用户权限接口");
			throw new GlobalException("没有实现获取用户权限接口");
		}
		List<String> permissions = hutuPermissionService.getUserPermissions(1);
		RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
		boolean havePermission = false;
		if (permissions != null && permissions.size() > 0) {
			String[] reqPermissions = requiresPermissions.value();
			if (reqPermissions.length > 0) {
				if (requiresPermissions.logical().equals(Logical.AND)) {
					havePermission = permissions.containsAll(Arrays.asList(reqPermissions));
				} else {
					for (String permission : reqPermissions) {
						if (permissions.contains(permission)) {
							havePermission = true;
							break;
						}
					}
				}
			}
		}
		if (!havePermission){
			logger.info("无权限访问,需要权限："+ Arrays.toString(requiresPermissions.value()));
			throw new GlobalException(ErrorMsgEnum.UNAUTHORIZED);
		}
	}
}
