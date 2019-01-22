package com.hutu.hutuupms;

import com.hutu.common.entity.R;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HutuUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutuUpmsApplication.class, args);
    }
    @RestController
    class test{
            @GetMapping("hello")
            public R hello(){
                return R.ok("hello");
            }
    }
}

