package com.alibaba.csp.sentinel.dashboard.rule.nacos.cluster;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc ClusterAssignNacosProvider
 * @author hutu
 * @date 2020/12/23 3:38 下午
 */
@Component("clusterAssignNacosProvider")
@Primary
public class ClusterAssignNacosProvider implements DynamicRuleProvider<List<ClusterAppAssignMap>> {

	@Autowired
	private ConfigService configService;

	@Autowired
	private Converter<String, List<ClusterAppAssignMap>> converter;

	@Override
	public List<ClusterAppAssignMap> getRules(String appName) throws Exception {
		String rules = configService.getConfig(appName + NacosConfigUtil.CLUSTER_MAP_DATA_ID_POSTFIX,
				NacosConfigUtil.GROUP_ID, 3000);
		if (StringUtil.isEmpty(rules)) {
			return new ArrayList<>();
		}
		return converter.convert(rules);
	}

}
