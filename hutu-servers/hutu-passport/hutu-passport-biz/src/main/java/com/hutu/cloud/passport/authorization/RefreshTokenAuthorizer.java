package com.hutu.cloud.passport.authorization;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.core.exception.GlobalException;
import com.hutu.cloud.core.utils.JsonUtil;
import com.hutu.cloud.core.entity.LoginUser;
import com.hutu.cloud.passport.constant.PassportConstant;
import com.hutu.cloud.passport.enums.PassportStatusEnum;
import com.hutu.cloud.token.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 刷新token实现
 *
 * @author hutu
 * @date 2021/3/31 3:08 下午
 */
@Component
public class RefreshTokenAuthorizer implements Authorizer {

	public static final String GRANT_TYPE = "refreshToken";

	@Autowired
	TokenService tokenService;

	@Override
	public LoginUser grant(Map<String, String> params) {
		String refreshToken = params.get("refreshToken");
		String jwt = tokenService.get(PassportConstant.CACHE_REFRESH_TOKEN_PREFIX + refreshToken);
		if (StrUtil.isEmpty(jwt)) {
			throw new GlobalException(PassportStatusEnum.REFRESH_TOKEN_NOT_FOUND);
		}
		Claims claims = tokenService.parse(jwt);
		LoginUser loginUser = JsonUtil.parse(claims.getSubject(), LoginUser.class);
		return loginUser;
	}

}
