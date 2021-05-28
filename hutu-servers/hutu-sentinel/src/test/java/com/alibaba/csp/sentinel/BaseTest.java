package com.alibaba.csp.sentinel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class BaseTest {

	protected static volatile Long startTime;

	@BeforeAll
	public static void beforeTest() {
		startTime = System.currentTimeMillis();
	}

	@AfterAll
	public static void afterTest() {
		System.out.println("test method took " + (System.currentTimeMillis() - startTime) + " ms!");
	}

}
