# nacos说明
### nacos集群部署
- ~~目前三台实例部署在一台物理机上~~
   - ~~192.168.2.46:8847~~             
   - ~~192.168.2.46:8848~~
   - ~~192.168.2.46:8849~~
- 目前只有一台物理机，所以不在集群，只保留一个节点
  - 192.168.2.221:8848
- 常用命令
  - ./startup.sh -m standalone (不加 -m standalone 标识集群模式)  
- 集群部署
  * 官方参考：[Nacos集群部署官方说明文档](https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html)
   
### 注意事项
- 在部署nacos时，如果mysql版本>8.0则需要手动将驱动放入根目录plugins中的mysql中，nacos会自动识别新驱动
- nacos默认是持久化到文件，如果改为mysql需修改配置文件，具体见[Nacos持久化到mysql](https://nacos.io/zh-cn/docs/deployment.html)

  
  