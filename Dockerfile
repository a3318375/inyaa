FROM openjdk:8-jdk-alpine
MAINTAINER 184608371@qq.com

#对外暴漏的端口号
EXPOSE 8082

WORKDIR /

#将本地项目jar包拷贝到Docker容器中的位置
RUN cp ./inyaa-web/build/libs/inyaa-web.jar ./

#开机启动
ENTRYPOINT ["java","-jar","/inyaa-web.jar"]