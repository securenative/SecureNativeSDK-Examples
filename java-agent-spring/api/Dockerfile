# Dockerfile for Java demo website
## For develpoment run:
##      docker build -t securenative-java-demo .
##      docker run -it -p8080:8080 -e SECURENATIVE_API_KEY=<api_key> securenative-java-demo

FROM openjdk:11

RUN DEBIAN_FRONTEND=noninteractive apt-get -y update
WORKDIR /usr/local/demo

COPY . ./
EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:/usr/local/demo/lib/securenative-java-agent-1.2-jar-with-dependencies.jar", "-jar", "/usr/local/demo/lib/java-api-1.0.jar"]
