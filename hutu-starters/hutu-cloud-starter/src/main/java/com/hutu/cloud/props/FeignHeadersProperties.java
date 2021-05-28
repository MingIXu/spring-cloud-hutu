package com.hutu.cloud.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * feign Headers 配置
 *
 * @author hutu
 * @date 2021/3/5 3:48 下午
 */
@Getter
@Setter
@ConfigurationProperties("hutu.feign.headers")
public class FeignHeadersProperties {

	/**
	 * RestTemplate 和 Fegin 透传到下层的 Headers 名称表达式
	 */
	@Nullable
	private String pattern = "Tempest*";

	/**
	 * RestTemplate 和 Fegin 透传到下层的 Headers 名称列表
	 */
	private List<String> allowed = Arrays.asList("X-Real-IP", "x-forwarded-for", "authorization", "token",
			"Authorization");

}
