package com.hutu.cloud.passport.controller;

import com.hutu.cloud.core.R;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.entity.LoginUser;
import com.hutu.cloud.core.utils.IdUtils;
import com.hutu.cloud.passport.authorization.Authorizer;
import com.hutu.cloud.passport.authorization.AuthorizerFactory;
import com.hutu.cloud.passport.constant.PassportConstant;
import com.hutu.cloud.passport.entity.AuthVO;
import com.hutu.cloud.passport.util.PassportUtil;
import com.hutu.cloud.token.enums.TokenTypeEnum;
import com.hutu.cloud.token.service.TokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.hutu.cloud.passport.constant.PassportConstant.*;

/**
 * TODO : 1. 用户在线管理 2. 强制下线 3. 令牌签发，认证，刷新 4. token存读redis
 *
 * @author hutu
 * @date 2021/3/31 5:27 下午
 */
@RestController
public class AuthController {

	@Autowired
	TokenService tokenService;

	/**
	 * 获取token
	 */
	@PostMapping("token")
	@ApiOperation(value = "获取认证token", notes = "传入租户ID:tenantId,账号:account,密码:password")
	public R<AuthVO> token(
			@ApiParam(value = "授权类型", required = true) @RequestParam(defaultValue = DEFAULT_GRANT_TYPE,
					required = false) String grantType,
			@ApiParam(value = "租户ID", required = true) @RequestParam(defaultValue = DEFAULT_TENANT_ID,
					required = false) String tenantId,
			@ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
			@ApiParam(value = "账号") @RequestParam(required = false) String account,
			@ApiParam(value = "密码") @RequestParam(required = false) String password) {
		// 获取授权器
		Authorizer authorizer = AuthorizerFactory.getAuthorizer(grantType);
		// 获取客户端类型
		String clientType = PassportUtil.getClientType();

		HashMap<String, String> params = new HashMap<>(6);
		params.put("tenantId", tenantId);
		params.put("account", account);
		params.put("password", password);
		params.put("grantType", grantType);
		params.put("refreshToken", refreshToken);
		params.put("clientType", clientType);

		// 获取登录用户信息
		LoginUser loginUser = authorizer.grant(params);
		// 组装返回前端对象
		AuthVO authVO = getAuthVO(clientType, loginUser);

		return R.ok(authVO);
	}

	private AuthVO getAuthVO(String clientType, LoginUser loginUser) {
		AuthVO authVO = new AuthVO()
				.setToken(PassportConstant.CACHE_TOKEN_PREFIX + clientType + StringPool.DASH + IdUtils.fastUUID())
				.setRefreshToken(PassportConstant.CACHE_REFRESH_TOKEN_PREFIX + clientType + StringPool.DASH
						+ IdUtils.fastUUID());
		String tokenValue = StringPool.EMPTY;
		String refreshTokenValue = tokenService.create(authVO.getRefreshToken(), loginUser,
				TokenTypeEnum.REFRESH_TOKEN);

		if (CLIENT_TYPE_WEB.equals(clientType)) {
			tokenValue = tokenService.create(authVO.getToken(), loginUser, TokenTypeEnum.ACCESS_TOKEN);
			authVO.setExpire(TokenTypeEnum.ACCESS_TOKEN.getExpire());
		}
		else if (CLIENT_TYPE_APP.equals(clientType)) {
			// 根据业务定制
			tokenValue = tokenService.create(authVO.getToken(), loginUser, TokenTypeEnum.ONE_DAY_TOKEN);
			authVO.setExpire(TokenTypeEnum.ONE_DAY_TOKEN.getExpire());
		}

		tokenService.save(authVO.getToken(), tokenValue, authVO.getExpire());
		tokenService.save(authVO.getRefreshToken(), refreshTokenValue, TokenTypeEnum.REFRESH_TOKEN.getExpire());

		return authVO;
	}

}
