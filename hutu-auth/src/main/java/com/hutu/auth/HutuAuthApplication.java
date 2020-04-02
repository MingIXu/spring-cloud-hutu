package com.hutu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.hutu.api")
@SpringBootApplication
public class HutuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutuAuthApplication.class, args);
    }
}

