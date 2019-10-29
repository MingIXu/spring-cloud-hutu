package com.hutu.gateway.route;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author hutu
 * @date 2019-10-29 17:55
 */
@Slf4j
@Component
public class DynamicRouteServiceImplByNacos {

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;
    @Value("${spring.cloud.nacos.dataId}")
    public String dataId;
    @Value("${spring.cloud.nacos.group}")
    public String group;
    @Value("${spring.cloud.nacos.discovery.serverAddr}")
    public String serverAddr;

    @PostConstruct
    public void init() {
        dynamicRouteByNacosListener(dataId,group);
    }

    /**
     * 监听Nacos Server下发的动态路由配置
     * @param dataId
     * @param group
     */
    public void dynamicRouteByNacosListener (String dataId, String group){
        try {
            ConfigService configService= NacosFactory.createConfigService(serverAddr);
            String content = configService.getConfig(dataId, group, 5000);
            System.out.println("nacos 动态路由信息");
            updateRoute(content);
            configService.addListener(dataId, group, new Listener()  {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    updateRoute(configInfo);
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.info("更新路由规则失败");
            //todo 提醒:异常自行处理此处省略
        }
    }

    /**
     * 更新nacos中的路由信息
     */
    private void updateRoute(String configInfo) {
        try {
            List<RouteDefinition> gatewayRouteDefinitions = JSON.parseArray(configInfo, RouteDefinition.class);
            for (RouteDefinition routeDefinition : gatewayRouteDefinitions){
                log.info("遍历:" + routeDefinition.toString());
                String message = dynamicRouteService.update(routeDefinition);
                log.info("route update: " + message);
            }
        }catch (Exception e){
            log.error("更新配置出错:",e);
        }
    }

}
