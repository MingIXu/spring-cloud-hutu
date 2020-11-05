# Redis相关说明

### 集群情况
- 开发环境
  > 192.168.2.221 端口：7000，7001，7002，7003，7004，7005
  
### 集群部署
- 集群部署
  * 官方参考：[Redis集群部署官方文档](https://redis.io/topics/cluster-tutorial)
  * 部署步骤：
    - 从官方下载最新发行版
    - 部署命令
    ```
    redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 \
    127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1
    ```
- Redis相关命令

  * redis-cli不能用
    > cp redis-cli /usr/local/bin
  * 连接redis
    > redis-cli -h 127.0.0.1 -p 6379
  * 关闭redis
    > edis-cli -h 127.0.0.1 -p 6379 shutdown 或 kill -9 
