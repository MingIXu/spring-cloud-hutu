package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.cloud.nacos.NacosConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 系统自适应限流规则nacos获取
 * @date 2020/4/27
 */
@Component("systemRuleNacosProvider")
@Primary
public class SystemRuleNacosProvider implements DynamicRuleProvider<List<SystemRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public List<SystemRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntitiesFromNacos(
                nacosConfigManager.getConfigService(),
                appName,
                NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                SystemRuleEntity.class
        );
    }
}
