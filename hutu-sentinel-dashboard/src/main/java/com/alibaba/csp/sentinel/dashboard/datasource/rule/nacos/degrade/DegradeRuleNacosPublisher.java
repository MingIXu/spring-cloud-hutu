package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 熔断降级规则nacos存储
 * @date 2020/4/27
 */
@Component("degradeRuleNacosPublisher")
@Primary
public class DegradeRuleNacosPublisher implements DynamicRulePublisher<List<DegradeRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public void publish(String app, List<DegradeRuleEntity> degradeRuleEntities) throws Exception {
        NacosConfigUtil.setRuleStringToNacos(
                nacosConfigManager.getConfigService(),
                app,
                NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX,
                degradeRuleEntities
        );
    }
}
