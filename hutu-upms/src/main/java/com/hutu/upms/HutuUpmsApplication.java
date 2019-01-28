package com.hutu.upms;
import com.hutu.cache.annotation.CacheExpire;
import com.hutu.cache.annotation.EnableHutuCache;
import com.hutu.common.entity.R;
import com.hutu.upms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.hutu.cache.constant.CacheConstant.CACHE_NAME;


@SpringBootApplication
@EnableHutuCache
public class HutuUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutuUpmsApplication.class, args);
    }

    @Autowired
    RedisTemplate redisTemplate;

    @RestController
    class test {
        @Cacheable(value = CACHE_NAME, unless = "#result == null or #result.empty")
        @CacheExpire(expire = 10000)
        @GetMapping("hello")
        public R hello(String name) {
            ArrayList<Object> list = new ArrayList<>();
            list.add("haode ");
            list.add(new User("ming", 18));
            System.out.println("hello: " + name);
            return R.ok("hello: " + name).put("list", list);
        }
    }
}

