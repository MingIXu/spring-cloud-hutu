package com.alibaba.csp.sentinel.dashboard.rule.nacos.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
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
 * @desc DegradeRuleNacosPublisher
 * @author hutu
 * @date 2020/12/2 5:46 下午
 */
@Component("degradeRuleNacosPublisher")
@Primary
public class DegradeRuleNacosPublisher implements DynamicRulePublisher<List<DegradeRuleEntity>> {

	@Autowired
	private ConfigService configService;

	@Autowired
	private Converter<List<DegradeRuleEntity>, String> converter;

	@Override
	public void publish(String app, List<DegradeRuleEntity> rules) throws Exception {
		AssertUtil.notEmpty(app, "app name cannot be empty");
		if (rules == null) {
			return;
		}
		configService.publishConfig(app + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID,
				converter.convert(rules));
	}

}
