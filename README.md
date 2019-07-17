## 基于阿里巴巴 spring-cloud-alibaba 的微服务架构

技术栈：
```
注册与配置中心   - nacos
服务限流与降级   - sentinel
网关服务        - spring-cloud-gateway
缓存  - jetCache
```
目录：
```
├─hutu-auth 鉴权中心
│  
├─hutu-common 公共模块
│  ├─hutu-common-cache 公共缓存模块
│  ├─hutu-common-core 公共核心模块
│  ├─hutu-common-db 数据库配置模块
│  ├─hutu-common-job 公共任务调度模块
│  ├─hutu-common-log 公共日志模块
│  ├─hutu-common-security 公共安全模块
│  ├─hutu-common-swagger 公共api展示模块
│  └─hutu-common-transaction 公共事务模块
│ 
├─hutu-config 配置模块
│ 
├─hutu-dependencies 项目依赖模块
│ 
├─hutu-gateway 网关模块
│ 
├─hutu-upms 用户中心
│ 
├─hutu-visual 可视化模块
```
