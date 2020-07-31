
package com.hutu.constant;

/**
 * 安全常量
 *
 * @author hutu
 * @date 2020/6/18 11:15 上午
 */
public interface SecurityConstant {

    /**
     * 默认排除的路径 patterns
     */
    String[] DEFAULT_EXCLUDE_PATTERNS = {
            "/error",
            "/actuator/**",
            "/test/**"
    };
}
