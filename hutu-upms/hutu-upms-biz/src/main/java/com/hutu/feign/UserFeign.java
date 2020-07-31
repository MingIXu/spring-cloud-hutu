package com.hutu.feign;

import com.hutu.annotation.PreAuth;
import com.hutu.annotation.SkipAuth;
import com.hutu.core.R;
import com.hutu.entity.User;
import com.hutu.service.UserService;
import com.hutu.utils.token.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 测试rest远程调用
 *
 * @author hutu
 * @date 2020/5/20 2:29 下午
 */
@Slf4j
@Api(tags = "测试rest远程调用")
@RestController
public class UserFeign {


    @Autowired
    UserService userService;

    @PreAuth("user:add")
    @ApiOperation("通过id获取用户信息")
    @GetMapping("user/{id}")
    public R<User> getUserById(@PathVariable("id") Long id) {
        System.out.println("====== token : "+ TokenUtil.getTokenString());
        User user = userService.getById(id);
        log.info("user is {}", user);
        return R.ok(user);
    }

    @SkipAuth
    @ApiOperation("通过id获取用户信息")
    @GetMapping("/hello")
    public R hello() {
        log.info("hello");
        return R.ok("this is hello", LocalDateTime.now());
    }
}
