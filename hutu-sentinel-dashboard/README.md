## hutu-sentinel-dashboard-plus
### 对 sentinel-dashboard 的增强
* 基于 1.8.1 的版本
* 规则配置持久化到 nacos -> mysql 中
* 实时监控数据持久化到 es 中 ->  Prometheus 等展示
> 注意：依赖的sentinel-dashboard需要自己拉源码打包成依赖jar包，注意需要去掉goal的repackage目标，无官方jar包可依赖
