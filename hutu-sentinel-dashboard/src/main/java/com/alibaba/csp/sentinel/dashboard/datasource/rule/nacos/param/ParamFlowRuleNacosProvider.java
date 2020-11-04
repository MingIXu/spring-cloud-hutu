package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.cloud.nacos.NacosConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 热点参数限流规则nacos获取
 * @date 2020/4/27
 */
@Component("paramFlowRuleNacosProvider")
@Primary
public class ParamFlowRuleNacosProvider implements DynamicRuleProvider<List<ParamFlowRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public List<ParamFlowRuleEntity> getRules(String appName) throws Exception {

        return NacosConfigUtil.getRuleEntitiesFromNacos(
                nacosConfigManager.getConfigService(),
                appName,
                NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX,
                ParamFlowRuleEntity.class
        );
    }
}
