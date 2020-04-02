//package com.hutu.common.cache.rest;
//
//import io.lettuce.core.RedisClient;
//import io.lettuce.core.api.sync.RedisCommands;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * 缓存管理
// *
// * @author hutu
// * @date 2019/1/25
// */
//@RestController
//@RequestMapping("cache")
//public class CacheManagerRest {
//    @Autowired
//    private RedisClient redisClient;
//
//    public RedisCommands<String, String> getCommands() {
//        return redisClient.connect().sync();
//    }
//
//    /**
//     * 获取缓存列表
//     * @return
//     */
//    @GetMapping("list")
//    public List getCache(@RequestParam(required = false) String keyWord) {
//        System.out.println("coming cache"+keyWord+"*");
//
//        List<String> keys = getCommands().keys(keyWord+"*");
//        ArrayList<Object> retList = new ArrayList<>();
//        for (String key: keys) {
//            HashMap<String, Object> map = new HashMap<>(2);
//            map.put("key",key);
//            map.put("value", getCommands().get(key));
//            retList.add(map);
//        }
//        return retList;
//    }
//    /**
//     * 删除缓存
//     */
//    @GetMapping("delete")
//    public boolean delete(String key){
//        return getCommands().del(key)!=0;
//    }
//
//    /**
//     * 续租
//     */
//    @GetMapping("expire")
//    public boolean expire(String key, Integer expire){
//        return getCommands().expire(key,expire);
//    }
//}
