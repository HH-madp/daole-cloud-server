FROM openjdk:8-jdk-alpine
VOLUME /tmp
#ARG JAR_FILE
#ADD ${JAR_FILE} /home/project/app.jar
ADD  assistant-rest-admin-0.0.4.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8005
