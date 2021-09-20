FROM openjdk:17-jdk-slim
ARG JAR_FILE=/target/client-1.0-jar-with-dependencies.jar
COPY ${JAR_FILE} client.jar
ENTRYPOINT java -jar /client.jar $SERVER_IP