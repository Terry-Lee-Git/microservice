#!/bin/bash

docker stop redis
docker rm redis
docker run --name user-redis -v /ops/data/redis/conf/redis.conf:/etc/redis/redis.conf -v /ops/data/redis/data:/data -p 6379:6379 -idt redis:3.2 redis-server /etc/redis/redis.conf --appendonly yes --restart always
