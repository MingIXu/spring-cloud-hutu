package com.hutu.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.hutu.annotation.Logical;
import com.hutu.annotation.SkipAuth;
import com.hutu.constant.AuthType;
import com.hutu.core.R;
import com.hutu.core.utils.UUIDUtils;
import com.hutu.utils.token.LoginUser;
import com.hutu.utils.token.TokenInfo;
import com.hutu.utils.token.TokenUtil;
import com.hutu.vo.CaptchaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证服务
 *
 * @author hutu
 * @date 2020/6/3 11:56 上午
 */
@Api(tags = "认证服务")
@RestController
public class AuthController {

    /**
     * 目前获取token三种情况：
     *  1. 账号密码
     *  2. refreshToken方式
     *  3. 验证码+账号密码
     */
    @PostMapping("token")
    @ApiOperation(value = "获取认证token", notes = "传入账号:account,密码:password")
    public R token(@ApiParam(value = "认证类型") @RequestParam(defaultValue = "password", required = false) String authType,
                   @ApiParam(value = "刷新令牌") @RequestParam(required = false) String refreshToken,
                   @ApiParam(value = "账号") @RequestParam(required = false) String account,
                   @ApiParam(value = "密码") @RequestParam(required = false) String password,
                   @ApiParam(value = "验证码key") @RequestParam(required = false) String captchaKey,
                   @ApiParam(value = "验证码") @RequestParam(required = false) String captchaCode) {

        // TODO
        //  1. 判断同一个账号密码错三次则锁定一分钟
        //  2. 校验验证码、账号、密码、刷新令牌是否合法

        LoginUser loginUser = null;
        if (AuthType.PASSWORD.value.equalsIgnoreCase(authType)){
            // TODO 1. 账号密码
            loginUser = new LoginUser()
                    .setAccount("001")
                    .setUserId(100100L)
                    .setUserName("小明");
        }else if (AuthType.CAPTCHA.value.equalsIgnoreCase(authType)){
            // TODO 2. refreshToken方式
        }else if (AuthType.REFRESH_TOKEN.value.equalsIgnoreCase(authType)){
            // TODO 3. 验证码+账号密码
        }

        TokenInfo token = TokenUtil.createToken(loginUser);
        return R.ok(token);
    }

    /**
     * 验证码过期时间15分钟
     */
    @CreateCache(expire = 60*15)
    private Cache<String, String> captchaCache;

    /**
     * 获取随机验证码
     *
     * @return 验证码的base64
     */
    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码")
    public R captcha() {
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        String key = UUIDUtils.generateShortUuid();
        // 验证码存入缓存,验证时通过key取出
        captchaCache.put(key, captcha.getCode());
        return R.ok(new CaptchaVo().setKey(key).setImg(captcha.getImageBase64()));
    }

    /**
     *
     * @param permissionCode 权限标识，多个用都好标出  例如：user:add,user:edit
     * @param logical 逻辑类型
     * @return boolean
     */
    @SkipAuth
    @GetMapping("/hasPermission")
    @ApiOperation(value = "是否有权限")
    public boolean hasPermission(String permissionCode, int logical) {
        System.out.println("================coming passport");
        // TODO 判断该用户是否有权限,基于RBAC鉴权
        //  1.数据库保存 用户-角色-资源 关系
        //  2.查询该用户拥有角色是否拥有该权限

        // 逻辑为 Logical.AND 所有权限都得满足，否则满足一个即可
        if (Logical.AND.value == logical){

        }else {

        }
        return true;
    }
}
