FROM openjdk:21
WORKDIR app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} connectmysql-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","connectmysql-1.0.0.jar"]