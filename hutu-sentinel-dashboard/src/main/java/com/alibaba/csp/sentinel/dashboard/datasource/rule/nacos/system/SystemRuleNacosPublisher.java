package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.cloud.nacos.NacosConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 系统自适应限流规则nacos存储
 * @date 2020/4/27
 */
@Component("systemRuleNacosPublisher")
@Primary
public class SystemRuleNacosPublisher implements DynamicRulePublisher<List<SystemRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public void publish(String app, List<SystemRuleEntity> systemRuleEntities) throws Exception {

        NacosConfigUtil.setRuleStringToNacos(
                nacosConfigManager.getConfigService(),
                app,
                NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                systemRuleEntities
        );
    }
}
