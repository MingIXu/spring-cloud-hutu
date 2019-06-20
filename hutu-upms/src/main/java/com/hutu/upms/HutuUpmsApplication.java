package com.hutu.upms;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.hutu.common.core.entity.R;
import com.hutu.common.cache.annotation.EnableHutuCache;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableSwagger2Doc
@EnableMethodCache(basePackages = "com.hutu.upms")
@EnableDiscoveryClient
@SpringBootApplication
@EnableHutuCache
public class HutuUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutuUpmsApplication.class, args);
    }

    @RestController
    class Test {
        @RequestMapping("get")
        @CacheRefresh(refresh = 50,stopRefreshAfterLastAccess = 600)
        @Cached(name = "test-",key = "#name",expire = 60)
        public R getHelloWord(String name){
            System.out.println("hello "+name);
            return R.ok().put("helloWord",name);
        }
        @RequestMapping("update")
        @CacheUpdate(name = "test-",key = "#name",value="#name")
        public boolean updateHelloWord(String name){
            System.out.println("update "+name);
            return true;
        }
        @RequestMapping("del")
        @CacheInvalidate(name = "test-", key = "#name")
        public boolean delHelloWord(String name){
            System.out.println("del "+name);
            return true;
        }
    }
}

