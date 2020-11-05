# Seata快速开始

### HA集群部署
- 下载最新稳定版：[github地址](https://github.com/seata/seata/releases) 
- 修改 conf/registry.conf 配置
  > 注册中心和配置中心默认是file这里改为nacos；设置 registry 和 config 节点中的type为nacos，修改serverAddr为你的nacos节点地址。

    ```
    registry {
      # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
      type = "nacos"
    
      nacos {
        application = "seata-server"
        serverAddr = "192.168.2.221"
        namespace = "132439b2-b383-4fd3-a956-ab4a91a49c9a"
        cluster = "default"
        username = ""
        password = ""
      }
    }
    
    config {
      # file、nacos 、apollo、zk、consul、etcd3
      type = "nacos"
    
      nacos {
        serverAddr = "192.168.2.221"
        namespace = "132439b2-b383-4fd3-a956-ab4a91a49c9a"
        cluster = "default"
        group = "SEATA_GROUP"
        username = ""
        password = ""
      }
    }
    
    ```
- 修改配置文件
    1. 从 [这里](https://github.com/seata/seata/tree/develop/script/config-center) 拷贝 config.txt 文件
    2. 修改 service.vgroup_mapping 为自己应用对应的名称；如果有多个服务，添加相应的配置
    3. 修改 store.mode 为db，并修改数据库相关配置
    4. 从 [这里](https://github.com/seata/seata/tree/develop/script/config-center/naocs) 拷贝 nacos-config.sh 文件
    5. 将上面拷贝的 config.txt 与 nacos-config.sh 拷贝到一个文件夹中执行下面命令将配置写入nacos
    ```sh
        sh nacos-config.sh -h 192.168.2.221:8848
    ```
- 初始化数据库
   * 数据库脚本在 [这里](https://github.com/seata/seata/tree/develop/script/server/db)
   * 记住每个业务数据库需要创建日志回滚表，脚本在 [这里](https://github.com/seata/seata/tree/develop/script/client) 根据数据库类型与分布式事务模式选择
     
- 启动TC seata-server  
    * 执行 nohup sh bin/seata-server.sh -p 18091 -n 1 & 命令，启动第一个 TC Server 在后台。
    * 执行 nohup sh bin/seata-server.sh -p 28091 -n 2 & 命令，启动第二个 TC Server 在后台,启动两个保证高可用。
    * p：Seata TC Server 监听的端口。
    * n：Server node。在多个 TC Server 时，需区分各自节点，用于生成不同区间的 transactionId 事务编号，以免冲突。

### Client端配置示例
```yaml
# Seata 配置项，对应 SeataProperties 类
seata:
  application-id: ${spring.application.name} # Seata 应用编号，默认为 ${spring.application.name}
  tx-service-group: ${spring.application.name}-group # Seata 事务组编号，用于 TC 集群名
  # Seata 服务配置项，对应 ServiceProperties 类
  service:
    # 虚拟组和分组的映射
    vgroup-mapping:
      storage-service-group: default # 此处因为${spring.application.name}-group = storage-service-group与服务端虚拟分组对应
  # Seata 注册中心配置项，对应 RegistryProperties 类
  registry:
    type: nacos # 注册中心类型，默认为 file
    nacos:
      cluster: default # 使用的 Seata 分组
      namespace: ${nacos.namespace} # Nacos 命名空间
      serverAddr: ${nacos.addr} # Nacos 服务地址
```   