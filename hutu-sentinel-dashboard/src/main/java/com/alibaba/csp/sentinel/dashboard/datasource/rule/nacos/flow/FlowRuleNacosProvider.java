package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 限流规则nacos获取
 * @date 2020/4/27
 */
@Component("flowRuleNacosProvider")
@Primary
public class FlowRuleNacosProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntitiesFromNacos(
                nacosConfigManager.getConfigService(),
                appName,
                NacosConfigUtil.FLOW_DATA_ID_POSTFIX,
                FlowRuleEntity.class
        );
    }
}
