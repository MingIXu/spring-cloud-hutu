package com.hutu.upms;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.hutu.common.cache.annotation.EnableHutuCache;
import com.hutu.common.core.entity.R;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableSwagger2Doc
@EnableHutuCache
@EnableDiscoveryClient
@SpringBootApplication
public class HutuUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HutuUpmsApplication.class, args);
    }

    @RestController
    class Test {
        @RequestMapping("get")
        @CacheRefresh(refresh = 50,stopRefreshAfterLastAccess = 600)
        @Cached(name = "test-",key = "#name",expire = 60)
        public String getHelloWord(String name){
            System.out.println("hello "+name);
            return name;
        }
        @RequestMapping("update")
        @CacheUpdate(name = "test-",key = "#name",value="#name")
        public String updateHelloWord(String name){
            System.out.println("update "+name);
            return name;
        }
        @RequestMapping("del")
        @CacheInvalidate(name = "test-", key = "#name")
        public boolean delHelloWord(String name){
            System.out.println("del "+name);
            return true;
        }
        /**
         * 测试限流
         * @return
         */
        @SentinelResource(value = "test",fallback = "fallbackTest")
        @GetMapping("/test")
        public R test(){
            return R.ok().put("info","123456");
        }
        public R fallbackTest(){
            return R.error("limiting ");
        }
    }
}

