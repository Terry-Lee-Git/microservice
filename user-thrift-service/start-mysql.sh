#!/bin/bash
docker stop user-mysql
docker rm user-mysql
docker run --name user-mysql -v /ops/data/mysql/conf:/etc/mysql/conf.d -v /ops/data/mysql/data:/var/lib/mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7  --restart always