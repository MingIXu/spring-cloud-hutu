package com.hutu.demo;

import com.hutu.common.core.entity.R;
import com.hutu.upms.api.RemoteLogService;
import com.hutu.upms.api.RemoteLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.hutu.upms.api")
@SpringBootApplication
public class ServiceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDemoApplication.class, args);
    }

    @Autowired
    RemoteLogService remoteLogService;
    @Autowired
    RemoteLoginService remoteLoginService;
    @RestController
    class Test {
        @RequestMapping("get")
        public String getHelloWord(String name){
            System.out.println("hello "+name);
            return name;
        }

        @RequestMapping("log")
        public R redLog(String id) {
            return remoteLogService.read(id);
        }
        @RequestMapping("login")
        public R login(String username, String password) {
            return remoteLoginService.login(username, password);
        }
    }
}

