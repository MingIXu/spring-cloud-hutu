package com.hutu.common.cache.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存管理
 *
 * @author hutu
 * @date 2019/1/25
 */
@RestController
@RequestMapping("cache")
public class CacheManagerRest {

//    @Autowired
//    RedisTemplate redisTemplate;
//    /**
//     * 获取缓存列表
//     * @return
//     */
//    @GetMapping("list")
//    public List getCache(@RequestParam(required = false) String keyWord) {
//
//        System.out.println("coming cache"+keyWord+"*");
//        Set keys = redisTemplate.keys(keyWord+"*");
//        List list = redisTemplate.opsForValue().multiGet(keys);
//        ArrayList<Object> retList = new ArrayList<>();
//        int n = 0;
//        for (Object key: keys) {
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("key",key.toString());
//            map.put("value", JSON.toJSONString(list.get(n)));
//            n++;
//            retList.add(map);
//        }
//        return retList;
//    }
//    /**
//     * 删除缓存
//     */
//    @GetMapping("delete")
//    public boolean delete(String key){
//        return redisTemplate.delete(key);
//    }
//
//    /**
//     * 续租
//     */
//    @GetMapping("expire")
//    public boolean expire(String key, Integer expire){
//        return redisTemplate.expire(key,expire, TimeUnit.SECONDS);
//    }
}
