package com.hutu.cloud.passport.controller;

import com.hutu.cloud.social.props.SocialProperties;
import com.hutu.cloud.social.utils.SocialUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 第三方登录先关
 *
 * @author hutu
 * @date 2021/4/2 2:32 下午
 */
@Slf4j
@RestController
@AllArgsConstructor
@ConditionalOnProperty(value = "social.enabled", havingValue = "true")
@Api(value = "第三方登陆", tags = "第三方登陆端点")
public class SocialController {

	private final SocialProperties socialProperties;

	/**
	 * 授权完毕跳转
	 * @param source 第三方授权平台，以本例为参考，该值为gitee（因为上面声明的AuthGiteeRequest）
	 */
	@ApiOperation(value = "授权完毕跳转")
	@RequestMapping("/oauth/render/{source}")
	public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
		response.sendRedirect(authorizeUrl);
	}

	/**
	 * 获取认证信息
	 * @param source 第三方授权平台，以本例为参考，该值为gitee（因为上面声明的AuthGiteeRequest）
	 */
	@ApiOperation(value = "获取认证信息")
	@RequestMapping("/oauth/callback/{source}")
	public Object login(@PathVariable("source") String source, AuthCallback callback) {
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		return authRequest.login(callback);
	}

	/**
	 * 撤销授权
	 * @param source 第三方授权平台，例如：github（因为上面声明的AuthGiteeRequest）
	 */
	@ApiOperation(value = "撤销授权")
	@RequestMapping("/oauth/revoke/{source}/{token}")
	public Object revokeAuth(@PathVariable("source") String source, @PathVariable("token") String token) {
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		return authRequest.revoke(AuthToken.builder().accessToken(token).build());
	}

	/**
	 * 续期accessToken
	 */
	@ApiOperation(value = "续期令牌")
	@RequestMapping("/oauth/refresh/{source}")
	public Object refreshAuth(@PathVariable("source") String source, String token) {
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		return authRequest.refresh(AuthToken.builder().refreshToken(token).build());
	}

}
