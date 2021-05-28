package com.hutu.cloud.core.constant;

/**
 * 公共常量
 *
 * @author hutu
 * @date 2019-12-12 18:46
 */
public interface CommonConstant {

	/**
	 * contentType
	 */
	String CONTENT_TYPE_NAME = "Content-type";

	/**
	 * 基础包
	 */
	String BASE_PACKAGES = "com.hutu.cloud";

	/**
	 * mybatis的接口mapper包
	 */
	String MAPPER_PACKAGES = "com.hutu.cloud.mapper.**";

	/**
	 * mybatis的接口mapper包扩展
	 */
	String MAPPER_PACKAGES_EXTRA = "com.hutu.cloud.**.mapper.**";

	/**
	 * 日志链路追踪id信息头
	 */
	String TRACE_ID_HEADER = "x-traceId-header";

	/**
	 * 日志链路追踪id日志标志
	 */
	String LOG_TRACE_ID = "traceId";

	/**
	 * 锁前缀
	 */
	String LOCK_KEY_PREFIX = "lockKey:";

}
