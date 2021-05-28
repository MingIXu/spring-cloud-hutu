package com.hutu.cloud.token.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @desc TokenProperties
 * @author hutu
 * @date 2021/4/1 10:33 上午
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = TokenProperties.PREFIX)
public class TokenProperties {

	final static String PREFIX = "hutu.token";

	/**
	 * base64的签名key
	 */
	String secretKey = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

}
