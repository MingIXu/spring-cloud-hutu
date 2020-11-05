# RocketMQ说明

### RocketMQ部署

1. 部署步骤

   - 官方参考：
     * [RocketMQ集群官方说明文档](http://rocketmq.apache.org/docs/quick-start/)
     * [RocketMQ的Github官方说明文档](https://github.com/apache/rocketmq/blob/evaluationMaster/docs/cn/operation.md)
   - 本方案采用多Master多Slave模式-同步双写
     * 从官方下载最新发行版
     * 拿两个节点作为NameServer服务器并分别启动
     ```
     ### 首先启动Name Server
     $ nohup sh mqnamesrv &
      
     ### 验证Name Server 是否启动成功
     $ tail -f ~/logs/rocketmqlogs/namesrv.log
     The Name Server boot success...
     ```
     * 拿四个节点搭建双主从集群(例如NameServer的IP为：192.168.1.1,192.168.1.2)
     ```
     ### 在机器A，启动第一个Master，
     $ nohup sh mqbroker -n 192.168.1.1:9876,192.168.1.2:9876 -c $ROCKETMQ_HOME/conf/2m-2s-sync/broker-a.properties &
      
     ### 在机器B，启动第二个Master
     $ nohup sh mqbroker -n 192.168.1.1:9876,192.168.1.2:9876 -c $ROCKETMQ_HOME/conf/2m-2s-sync/broker-b.properties &
      
     ### 在机器C，启动第一个Slave
     $ nohup sh mqbroker -n 192.168.1.1:9876,192.168.1.2:9876 -c $ROCKETMQ_HOME/conf/2m-2s-sync/broker-a-s.properties &
      
     ### 在机器D，启动第二个Slave
     $ nohup sh mqbroker -n 192.168.1.1:9876,192.168.1.2:9876 -c $ROCKETMQ_HOME/conf/2m-2s-sync/broker-b-s.properties &
     ```
   - **注意**
       1. 此处六个节点不是一台机器才可以这么部署，如果不是六台，比如一组主从在同一台机器则需要修改配置文件中store相关配置，保证路劲不冲突
       2. 建议broker配置文件中指定下brokerIP，应该在虚拟机环境下可能有多个网络ip导致自动识别ip错误。
2. 部署情况
   - [开发环境控制台](http://192.168.2.52:8080)

   - name-server(两个节点)：
     * 192.168.2.46:9876                  
     * 192.168.2.52:9876
     
   - broker(双主从同步部署)
    ```
    说明: 此为开发环境部署情况
    #Cluster Name     #Broker Name            #BID  #Addr                  #Version                #InTPS(LOAD)       #OutTPS(LOAD) #PCWait(ms) #Hour #SPACE
    DefaultCluster    broker-a                0     192.168.2.46:10911     V4_7_0                   0.00(0,0ms)         0.00(0,0ms)          0 441825.28 -1.0000
    DefaultCluster    broker-a                1     192.168.2.46:10010     V4_7_0                   0.00(0,0ms)         0.00(0,0ms)          0 441825.28 0.2294
    DefaultCluster    broker-b                0     192.168.2.52:10911     V4_7_0                   0.00(0,0ms)         0.00(0,0ms)          0 441825.28 -1.0000
    DefaultCluster    broker-b                1     192.168.2.52:10010     V4_7_0                   0.00(0,0ms)         0.00(0,0ms)          0 441825.28 0.2200
    ```
   
  