package com.hutu.cache.util;

import com.alicp.jetcache.Cache;

public class DictionaryCacheUtil {

    private static Cache<String, String> dicCache;

    public DictionaryCacheUtil(Cache<String, String> cache) {
        DictionaryCacheUtil.dicCache = cache;
    }

    public static String getText(String id){
        return dicCache.get(id);
    }
}
