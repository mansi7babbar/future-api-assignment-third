FROM ubuntu:16.04
FROM openjdk:8-jre-alpine
COPY target/scala-2.12/future-api-assignment-third-assembly-0.1.jar /app.war 
CMD ["/usr/bin/java", "-jar", "/app.war"]
