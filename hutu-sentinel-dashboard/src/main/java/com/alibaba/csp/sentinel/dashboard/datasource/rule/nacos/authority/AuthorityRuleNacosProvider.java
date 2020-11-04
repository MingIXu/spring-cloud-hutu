package com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.authority;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @description 授权规则nacos获取
 * @date 2020/4/27
 */
@Component("authorityRuleNacosProvider")
@Primary
public class AuthorityRuleNacosProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
        return NacosConfigUtil.getRuleEntitiesFromNacos(
                nacosConfigManager.getConfigService(),
                appName,
                NacosConfigUtil.AUTHORITY_DATA_ID_POSTFIX,
                AuthorityRuleEntity.class
        );
    }
}
