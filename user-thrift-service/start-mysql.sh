#!/bin/bash
cur_dir=pwd
docker stop user-mysql
docker rm user-mysql
docker run --name user-mysql -v ${cur_dir}/conf:/etc/mysql/conf.d -v ${cur_dir}/data:/var/lib/mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7
docker run --name user-mysql -v conf:/etc/mysql/conf.d -v data:/var/lib/mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7