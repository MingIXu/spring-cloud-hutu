package com.hutu.cloud.passport.authorization;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.core.exception.GlobalException;
import com.hutu.cloud.core.entity.LoginUser;
import com.hutu.cloud.passport.api.feign.LoginUserService;
import com.hutu.cloud.passport.enums.PassportStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 密码登录实现
 *
 * @author hutu
 * @date 2021/3/31 3:08 下午
 */
@Component
public class PasswordAuthorizer implements Authorizer {

	public static final String GRANT_TYPE = "password";

	@Autowired
	LoginUserService loginUserService;

	@Override
	public LoginUser grant(Map<String, String> params) {
		String tenantId = params.get("tenantId");
		String account = params.get("account");
		String password = params.get("password");
		LoginUser loginUserInfo = loginUserService.getLoginUserInfo(account, tenantId);
		String userPassword = loginUserInfo.getPassword();
		if (StrUtil.isEmpty(password)) {
			throw new GlobalException(PassportStatusEnum.PASSWORD_IS_EMPTY);
		}
		else if (!password.equals(userPassword)) {
			throw new GlobalException(PassportStatusEnum.USERNAME_OR_PASS_ERROR);
		}

		return loginUserInfo;
	}

}
