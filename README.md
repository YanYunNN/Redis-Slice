# Redis-Cluster
## Spring-boot-redis
`Redis缓存使用姿势盘点`
https://www.geekdigging.com/2019/09/24/2171701522/


## Springboot
* 1.整合Redis做缓存
* 2.开启Cluster支持
* 3.使用redis session共享（需要nginx做负载均衡）

### Redis集群
> ##################################################
>  
> Redis
>
> ##################################################

#查看进程
ps -ef|grep redis
kill -9 [pid]

##增加节点
```` 
redis-cli --cluster add-node --slave 192.168.1.102:7000 192.168.1.102:7003
````

#平衡槽slot
```` 
redis-cli --cluster rebalance --cluster-use-empty-masters  192.168.1.102:7000
````

##进入节点设为某个master的slave
````
redis-cli -h 192.168.1.102 -p 7000 -c

192.168.1.102:7000> cluster replicate  530b327b38e871d9067c862b331eb665f6bff5f0
````
