package com.hutu.controller;

import com.hutu.core.R;
import com.hutu.http.SsdevRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * jetCache缓存测试
 *
 * @author hutu
 * @date 2020/5/29 10:38 上午
 */
@Slf4j
@RestController
public class TestSsdevController {

    @Autowired
    SsdevRestTemplate restTemplate;

    @GetMapping("ssdev")
    public R testSsdev() {
        String serviceId = "eh.appConfigService";
        String serviceMethod = "queryAppConfigs";
        String token = "ecd8fb1b-c901-46bf-bd40-530881b1173c";
        String[] parameter = {"APP_SDK","ngari-doctor", "mobiledoctor", "android"};
        SsdevTest exchange = restTemplate.exchange(serviceId, serviceMethod, token, parameter,SsdevTest.class);
        System.out.println(exchange);
        return R.ok(exchange);
    }
    @GetMapping("getUser")
    public void getUser(){
        restTemplate.getUserInfo();
    }
}
