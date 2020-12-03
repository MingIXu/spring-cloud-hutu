package com.alibaba.csp.sentinel.dashboard.rule.nacos;

/**
 * @desc NacosConfigUtil
 *
 * @author hutu
 * @date 2020/12/2 5:47 下午
 */
public class NacosConfigUtil {

    public static final String GROUP_ID = "SENTINEL_GROUP";

    public static final String AUTHORITY_DATA_ID_POSTFIX = "-authority-rules";
    public static final String DEGRADE_DATA_ID_POSTFIX = "-degrade-rules";
    public static final String FLOW_DATA_ID_POSTFIX = "-flow-rules";
    public static final String PARAM_FLOW_DATA_ID_POSTFIX = "-param-rules";
    public static final String SYSTEM_DATA_ID_POSTFIX = "-system-rules";
    public static final String CLUSTER_MAP_DATA_ID_POSTFIX = "-cluster-map";

    /**
     * cc for `cluster-client`
     */
    public static final String CLIENT_CONFIG_DATA_ID_POSTFIX = "-cc-config";
    /**
     * cs for `cluster-server`
     */
    public static final String SERVER_TRANSPORT_CONFIG_DATA_ID_POSTFIX = "-cs-transport-config";
    public static final String SERVER_FLOW_CONFIG_DATA_ID_POSTFIX = "-cs-flow-config";
    public static final String SERVER_NAMESPACE_SET_DATA_ID_POSTFIX = "-cs-namespace-set";

    private NacosConfigUtil() {}

}
