package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.authority;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 授权规则nacos存储
 * @date 2020/4/27
 */
@Component("authorityRuleNacosPublisher")
@Primary
public class AuthorityRuleNacosPublisher implements DynamicRulePublisher<List<AuthorityRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public void publish(String app, List<AuthorityRuleEntity> authorityRuleEntities) throws Exception {
        NacosConfigUtil.setRuleStringToNacos(
                nacosConfigManager.getConfigService(),
                app,
                NacosConfigUtil.AUTHORITY_DATA_ID_POSTFIX,
                authorityRuleEntities
        );
    }
}
