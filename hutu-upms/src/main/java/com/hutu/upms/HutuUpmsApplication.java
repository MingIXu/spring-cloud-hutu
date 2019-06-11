package com.hutu.upms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class HutuUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutuUpmsApplication.class, args);
    }

    @RestController
    class test {
        @GetMapping("hello")
        public String hello(String name) {
            return "hello: " + name;
        }
    }
}

