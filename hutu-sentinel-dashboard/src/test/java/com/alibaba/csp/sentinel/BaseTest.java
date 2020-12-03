package com.alibaba.csp.sentinel;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTest{


    protected volatile Long startTime;

    @Before
    public void beforeTest(){
        startTime = System.currentTimeMillis();
    }

    @After
    public void afterTest(){
        System.out.println("test method took "+(System.currentTimeMillis()-startTime)+" ms!");
    }
}
