package com.hutu.cloud.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试用，加载springboot上下文环境
 *
 * @author hutu
 * @date 2021/3/23 5:53 下午
 */
@SpringBootApplication
public class ElasticApplication {

	public static void main(String[] args) {
		System.setProperty("spring.elasticsearch.rest.uris", "192.168.2.221:9200");
		SpringApplication.run(ElasticApplication.class, args);
	}

}
