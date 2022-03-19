package com.hutu.cloud.helloword;

import com.hutu.cloud.common.util.SpringUtils;
import com.hutu.cloud.security.annotation.PreAuth;
import com.hutu.cloud.security.enums.AuthType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWordApplication
 *
 * @author hutu
 * @date 2022/3/19 3:38 下午
 */
@SpringBootApplication
public class HelloWordApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWordApplication.class, args);
    }

    @RestController
    public static class HelloWord {
        @PreAuth(authType = AuthType.NONE)
        @GetMapping("test")
        public String test() {
            String hutu = SpringUtils.getProperty("hutu");
            return "hello: " + hutu;
        }
    }
}
