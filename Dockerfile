FROM openjdk:8-jre-alpine
RUN mkdir -p /app
WORKDIR /app
COPY ./run_jar.sh ./target/scala-2.13/app-assembly.jar ./
ENTRYPOINT ["./run_jar.sh"]
