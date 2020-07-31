
package com.hutu.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应返回
 *
 * @author hutu
 */
public class ResponseProvider {

    public static final String CODE = "code";
    private static final String MSG = "msg";
    private static final String DATA = "data";

    /**
     * 构建返回的JSON数据格式
     *
     * @param status  状态码
     * @param message 信息
     * @return
     */
    public static Map<String, Object> response(int status, String message) {
        Map<String, Object> map = new HashMap<>(16);
        map.put(CODE, status);
        map.put(MSG, message);
        map.put(DATA, null);
        return map;
    }

}
