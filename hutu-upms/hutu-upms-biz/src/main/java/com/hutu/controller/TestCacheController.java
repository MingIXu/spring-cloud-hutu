package com.hutu.controller;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.hutu.annotation.SkipAuth;
import com.hutu.core.R;
import com.hutu.entity.User;
import com.hutu.redisson.annotation.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * jetCache缓存测试
 *
 * @author hutu
 * @date 2020/5/29 10:38 上午
 */
@Slf4j
@RestController
public class TestCacheController {

    @SkipAuth
    @GetMapping("get")
    @CacheRefresh(refresh = 50, stopRefreshAfterLastAccess = 600)
    @Cached(name = "test-", key = "#name", expire = 60)
    public String getHelloWord(String name) {
        System.out.println("hello " + name);
        return name;
    }

    @GetMapping("update")
    @CacheUpdate(name = "test-", key = "#name", value = "#name")
    public String updateHelloWord(String name) {
        System.out.println("update " + name);
        return name;
    }

    @GetMapping("del")
    @CacheInvalidate(name = "test-", key = "#name")
    public boolean delHelloWord(String name) {
        System.out.println("del " + name);
        return true;
    }
    /**
     * 分布式锁测试
     */
    @Lock(key = "#user.account")
    @GetMapping("/lock")
    public R lock(User user) {
        try {
            log.info("locked");
            Thread.sleep(1000 * 5);
        } catch (Exception e){
            e.printStackTrace();
        }
        log.info("finished");
        return R.ok();
    }
}
