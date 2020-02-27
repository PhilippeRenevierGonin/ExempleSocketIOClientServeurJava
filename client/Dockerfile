FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/client-1.0-jar-with-dependencies.jar
COPY ${JAR_FILE} client.jar
ENTRYPOINT java -jar /client.jar 172.28.0.2