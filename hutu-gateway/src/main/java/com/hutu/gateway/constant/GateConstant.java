package com.hutu.gateway.constant;

import java.util.Arrays;
import java.util.HashSet;

public class  GateConstant {
    /**
     * 不处理的请求前缀
     */
    public static final String START_WITH = "upms,cms,api";
    public final static String DOU_HAO = ",";
    public final static String XIE_GANG = "/";
    public final static String TOKEN = "token";
    /**
     * 白名单
     */
    public final static HashSet<String> WHITE_PATHS = new HashSet<>(Arrays.asList("auth/login", "auth/test"));
}
