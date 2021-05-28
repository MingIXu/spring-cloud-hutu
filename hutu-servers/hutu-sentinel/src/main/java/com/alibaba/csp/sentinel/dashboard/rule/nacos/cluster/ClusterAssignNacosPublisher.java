package com.alibaba.csp.sentinel.dashboard.rule.nacos.cluster;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc ClusterAssignNacosPublisher
 * @author hutu
 * @date 2020/12/23 3:38 下午
 */
@Component("clusterAssignNacosPublisher")
@Primary
public class ClusterAssignNacosPublisher implements DynamicRulePublisher<List<ClusterAppAssignMap>> {

	@Autowired
	private ConfigService configService;

	@Autowired
	private Converter<List<ClusterAppAssignMap>, String> converter;

	@Override
	public void publish(String app, List<ClusterAppAssignMap> rules) throws Exception {
		AssertUtil.notEmpty(app, "app name cannot be empty");
		if (rules == null) {
			return;
		}
		configService.publishConfig(app + NacosConfigUtil.CLUSTER_MAP_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID,
				converter.convert(rules));
	}

}
