package com.hutu.upms;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableSwagger2Doc
//@EnableHutuCache
//@EnableMethodCache(basePackages = "com.hutu.upms")
@EnableDiscoveryClient
@SpringBootApplication
public class HutuUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutuUpmsApplication.class, args);
    }

//    @RestController
//    class Test {
//        @RequestMapping("get")
//        @CacheRefresh(refresh = 50,stopRefreshAfterLastAccess = 600)
//        @Cached(name = "test-",key = "#name",expire = 60)
//        public R getHelloWord(String name){
//            System.out.println("hello "+name);
//            return R.ok().put("helloWord",name);
//        }
//        @RequestMapping("update")
//        @CacheUpdate(name = "test-",key = "#name",value="#name")
//        public boolean updateHelloWord(String name){
//            System.out.println("update "+name);
//            return true;
//        }
//        @RequestMapping("del")
//        @CacheInvalidate(name = "test-", key = "#name")
//        public boolean delHelloWord(String name){
//            System.out.println("del "+name);
//            return true;
//        }
//    }
}

