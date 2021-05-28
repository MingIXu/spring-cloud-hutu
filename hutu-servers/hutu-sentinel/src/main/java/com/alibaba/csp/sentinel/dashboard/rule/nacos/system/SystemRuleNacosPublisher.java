package com.alibaba.csp.sentinel.dashboard.rule.nacos.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc SystemRuleNacosPublisher
 * @author hutu
 * @date 2020/12/2 5:47 下午
 */
@Component("systemRuleNacosPublisher")
@Primary
public class SystemRuleNacosPublisher implements DynamicRulePublisher<List<SystemRuleEntity>> {

	@Autowired
	private ConfigService configService;

	@Autowired
	private Converter<List<SystemRuleEntity>, String> converter;

	@Override
	public void publish(String app, List<SystemRuleEntity> rules) throws Exception {
		AssertUtil.notEmpty(app, "app name cannot be empty");
		if (rules == null) {
			return;
		}
		configService.publishConfig(app + NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID,
				converter.convert(rules));
	}

}
