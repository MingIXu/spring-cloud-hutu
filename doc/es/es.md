# elasticsearch 说明
### 部署情况
- 目前只一个节点：192.126.2.221：9200，测试用
- 可视化客户端：目前使用chrome插件 elasticsearch-head
- 从5.0开始 elasticsearch 安全级别提高了 不允许采用root帐号启动 所以我们要添加一个用户用来启动 elasticsearch

  开始之前先把防火墙关了 耽误事  
  ```
  [root@localhost ~]# systemctl stop firewalld.service
  [root@localhost~]# systemctl disable firewalld.service//禁止防火墙开机启动
  [root@localhost ~]# useradd es//创建es用户
  [root@localhost ~]# chown -R es:es /usr/local/elasticsearch-6.6.4///把目录权限赋予给es用户
  [root@localhost ~]# su es//切换至es用户
  [es@localhost root]$ 
  如果想要以守护进程的方式运行，可以添加-d参数：
  $ bin/elasticsearch -d
  ```