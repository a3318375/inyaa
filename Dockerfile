FROM openjdk:8-jdk-alpine

VOLUME /root/tmp
#将本地项目jar包拷贝到Docker容器中的位置
ADD inyaa-web/build/libs/inyaa-web.jar inyaa-web.jar

#开机启动
ENTRYPOINT ["java","-jar","/inyaa-web.jar"]