FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/serveur-1.0.jar
COPY ${JAR_FILE} serveur.jar
ENTRYPOINT java -jar /serveur.jar