package com.hutu.cloud.register.discovery;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosRegistrationCustomizer;
import com.hutu.cloud.register.enums.GrayTypeEnum;

import java.util.Map;

import static com.hutu.cloud.register.constant.GrayConstant.hutu_REGION_KEY;

/**
 * 自定义注册服务,注册扩展 metadata 信息
 *
 * @author hutu
 * @date 2021/4/27 11:26 上午
 */
public class MetadataRegistrationCustomizer implements NacosRegistrationCustomizer {

	@Override
	public void customize(NacosRegistration registration) {
		NacosDiscoveryProperties nacosDiscoveryProperties = registration.getNacosDiscoveryProperties();
		Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();

		String hutuRegion = System.getProperty(hutu_REGION_KEY);
		if (StrUtil.isNotEmpty(hutuRegion)) {
			metadata.put(GrayTypeEnum.REGION.getCode(), hutuRegion);
		}

	}

}
