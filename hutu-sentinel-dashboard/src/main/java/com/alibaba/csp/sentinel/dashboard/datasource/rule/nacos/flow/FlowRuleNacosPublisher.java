package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 限流规则nacos存储
 * @date 2020/4/27
 */
@Component("flowRuleNacosPublisher")
@Primary
public class FlowRuleNacosPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public void publish(String app, List<FlowRuleEntity> flowRuleEntities) throws Exception {
        NacosConfigUtil.setRuleStringToNacos(
                nacosConfigManager.getConfigService(),
                app,
                NacosConfigUtil.FLOW_DATA_ID_POSTFIX,
                flowRuleEntities
        );
    }
}
