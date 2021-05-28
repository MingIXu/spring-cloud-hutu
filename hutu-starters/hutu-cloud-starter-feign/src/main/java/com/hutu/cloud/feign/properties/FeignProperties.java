package com.hutu.cloud.feign.properties;

import com.hutu.cloud.feign.enums.RpcType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 默认限流熔断策略配置
 *
 * @author hutu
 * @date 2021/3/26 3:00 下午
 */
@Setter
@Getter
@ConfigurationProperties(prefix = FeignProperties.PREFIX)
public class FeignProperties {

	final static String PREFIX = "hutu.feign";

	RpcType rpcType = RpcType.TCP;

}
