FROM openjdk:8-jdk-alpine
COPY target/scala-2.12/future-api-assignment-third-assembly-0.1.jar /future-api-assignment-third-assembly-0.1.jar
ENTRYPOINT exec java $* -jar /future-api-assignment-third-assembly-0.1.jar
