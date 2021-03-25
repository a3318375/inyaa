FROM openjdk:8-jdk-alpine

#将本地项目jar包拷贝到Docker容器中的位置
ADD build/libs/inyaa-oauth.jar /
EXPOSE 8080
#开机启动
ENTRYPOINT ["java","-jar","/inyaa-oauth.jar"]
