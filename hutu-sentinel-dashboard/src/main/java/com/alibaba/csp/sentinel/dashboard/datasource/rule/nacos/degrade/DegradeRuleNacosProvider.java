package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 熔断降级规则nacos获取
 * @date 2020/4/27
 */
@Component("degradeRuleNacosProvider")
@Primary
public class DegradeRuleNacosProvider implements DynamicRuleProvider<List<DegradeRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public List<DegradeRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntitiesFromNacos(
                nacosConfigManager.getConfigService(),
                appName,
                NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX,
                DegradeRuleEntity.class
        );
    }
}
