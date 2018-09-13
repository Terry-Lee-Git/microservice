#!/bin/bash
cur_dir=pwd
docker stop redis
docker rm redis
docker run --name user-redis -v ${cur_dir}/conf/redis.conf:/etc/redis/redis_default.conf -v ${cur_dir}/data:/data -p 6379:6379 -idt redis:3.2
docker run --name user-redis -v conf/redis.conf:/etc/redis/redis.conf -v data:/data -p 6379:6379 -idt redis:3.2 redis-server /etc/redis/redis.conf --appendonly yes
#命令说明：
　　--name redis3 : 指定容器名称，这个最好加上，不然在看docker进程的时候会很尴尬。
　　-p 6699:6379 ： 指定端口映射，默认redis启动的是6379，至于外部端口不冲突就行。
　　-v $PWD/redis.conf:/etc/redis/redis.conf ： 将主机中当前目录下的redis.conf配置文件映射。
　　-v $PWD/data:/data -d redis:3.2 ： 将主机中当前目录下的data挂载到容器的/data
　　--redis-server --appendonly yes :在容器执行redis-server启动命令，并打开redis持久化配置\
　　注意事项：
　　　　如果不需要指定配置，-v $PWD/redis.conf:/etc/redis/redis.conf 可以不用 ，
　　　　redis-server 后面的那段 /etc/redis/redis.conf 也可以不用。