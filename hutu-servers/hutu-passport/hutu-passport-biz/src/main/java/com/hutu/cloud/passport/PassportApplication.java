package com.hutu.cloud.passport;

import com.hutu.cloud.feign.annotation.EnableCustomFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证鉴权中心
 *
 * @author hutu
 * @date 2021/3/4 5:47 下午
 */
@EnableCustomFeignClients
@SpringBootApplication
public class PassportApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassportApplication.class, args);
	}

}
