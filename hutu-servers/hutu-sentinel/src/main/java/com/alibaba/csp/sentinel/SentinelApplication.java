package com.alibaba.csp.sentinel;

import com.alibaba.csp.sentinel.init.InitExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @desc SentinelDashboardPlusApp
 * @author hutu
 * @date 2020/12/2 11:23 上午
 */
@SpringBootApplication
public class SentinelApplication {

	public static void main(String[] args) {
		triggerSentinelInit();
		SpringApplication.run(SentinelApplication.class, args);
	}

	private static void triggerSentinelInit() {
		new Thread(() -> InitExecutor.doInit()).start();
	}

}
