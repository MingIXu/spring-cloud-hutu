package com.hutu.cloud.security.endpoint;

import com.hutu.cloud.common.util.SpringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用信息
 *
 * @author hutu
 * @date 2021/3/12 3:34 下午
 */
@RestController
public class AppInfoEndpoint {

	/**
	 * 获取所有app的url列表
	 */
	@GetMapping("getAllUrl")
	public List<String> getAllUrl() {
		return SpringUtils.getAllUrl();
	}

}
