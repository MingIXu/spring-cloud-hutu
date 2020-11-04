package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 热点参数限流规则nacos存储
 * @date 2020/4/27
 */
@Component("paramFlowRuleNacosPublisher")
@Primary
public class ParamFlowRuleNacosPublisher implements DynamicRulePublisher<List<ParamFlowRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public void publish(String app, List<ParamFlowRuleEntity> paramFlowRuleEntities) throws Exception {
        NacosConfigUtil.setRuleStringToNacos(
                nacosConfigManager.getConfigService(),
                app,
                NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX,
                paramFlowRuleEntities
        );
    }
}
