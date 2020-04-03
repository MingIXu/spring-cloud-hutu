
package com.hutu.security.constant;

/**
 * 安全常量
 * @author hutu
 * @date 2019-12-13 10:53
 */
public interface SecureConstant {

    /**
     * 请求白名单
     */
    String[] WHITE_WORDS = {".css", ".js", ".html", ".map", ".woff", ".woff2", ".jpg", ".png", ".gif", ".jpeg", ".bmp",
            "ttf", "mp4", "m3u8", "flv", "pdf", "docx", "doc", "xlsx", "xls", ".txt", "/druid", "/error", "/configuration/ui",
            "/swagger-resources", "/v2/", "/services"};

}
