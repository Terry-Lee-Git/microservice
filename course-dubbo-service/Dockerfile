#course-dubbo-service Dockerfile
FROM openjdk:8-jre
MAINTAINER mayanghua "yh_ma@qq.com"

COPY target/course-dubbo-service-1.0-SNAPSHOT.jar /course-service.jar

ENTRYPOINT ["java","-jar","/course-service.jar"]
