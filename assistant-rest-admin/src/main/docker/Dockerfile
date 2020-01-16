#基于jdk8镜像进行构建，创建的镜像已经配置好java环境
FROM openjdk:8

#上述的pom中配置的JAR_FILE将会传入到该参数
#也可以在这里直接配置　ARG JAR_FILE=/target/web-1.0.jar
#用于配置spingboot应用maven打包生成的jar文件
VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} /home/project/app.jar

#暴露镜像的端口8452，其他端口不开放
EXPOSE 8005
