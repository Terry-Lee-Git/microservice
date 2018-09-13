#user-thrift-service Dockerfile
FROM openjdk:8-jre
MAINTAINER mayanghua "yh_ma@qq.com"

COPY target/user-thrift-service-1.0-SNAPSHOT.jar /user-service.jar

ENTRYPOINT ["java","-jar","/user-service.jar"]
