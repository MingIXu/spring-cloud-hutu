package com.hutu.controller;

import com.hutu.core.R;
import com.hutu.utils.token.TokenUtil;
import com.hutu.service.UpmsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * doSomething
 *
 * @author hutu
 * @date 2020/5/17 6:48 下午
 */
@RestController
public class TestController {

    @Resource
    UpmsService upmsService;

    @GetMapping("user/{id}")
    public R getUserById(@PathVariable("id") Long id){
        System.out.println("====== token : "+ TokenUtil.getTokenString());
        return upmsService.user(id);
    }
}
