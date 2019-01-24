package com.hutu.hutuupms;

import com.hutu.common.cache.CacheExpire;
import com.hutu.common.cache.EnableHutuCache;
import com.hutu.common.entity.R;
import com.hutu.hutuupms.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
@EnableHutuCache
public class HutuUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutuUpmsApplication.class, args);
    }
    @RestController
    @Cacheable(value = "cache", key = "#name", unless = "#result == null or #result.empty")
    @CacheExpire(expire = 1000)
    class test{
            @GetMapping("hello")
            public R hello(String name){
                ArrayList<Object> list = new ArrayList<>();
                list.add("haode ");
                list.add(new User("ming",18));
                System.out.println("hello: "+name);
                return R.ok("hello: "+name).put("list",list);
            }
    }
}

