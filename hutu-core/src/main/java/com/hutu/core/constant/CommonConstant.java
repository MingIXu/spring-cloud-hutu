package com.hutu.core.constant;

import com.hutu.core.utils.SignHelper;

/**
 * 公共常量
 *
 * @author hutu
 * @date 2019-12-12 18:46
 */
public interface CommonConstant {

    /**
     * token在header中的key
     */
    String TOKEN = "token";

    /**
     * 基础包
     */
    String BASE_PACKAGES = "com.hutu";

    /**
     * mybatis的接口mapper包
     */
    String MAPPER_PACKAGES = "com.hutu.mapper.**";

    String MAPPER_PACKAGES_EXTRA = "com.hutu.**.mapper.**";

    /**
     * 日志链路追踪id信息头
     */
    String TRACE_ID_HEADER = "x-traceId-header";

    /**
     * 日志链路追踪id日志标志
     */
    String LOG_TRACE_ID = "traceId";

    String LOCK_KEY_PREFIX = "lockKey:";
    /**
     * 网关调用标识
     */
    String GATEWAY_KEY = "gatewayKey";

    /**
     * 网关调用标识签名
     */
    String GATEWAY_KEY_SIGN = SignHelper.sign(GATEWAY_KEY);

    /**
     * 是否鉴权标识
     */
    String IS_AUTH = "isAuth";

}
