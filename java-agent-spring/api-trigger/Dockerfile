# Dockerfile for API calls trigger
## For develpoment run:
##      docker build -t securenative-java-trigger .
##      docker run -it -e SECURENATIVE_API_KEY=<api_key> -e BASE_URL=<base_url> securenative-java-trigger

FROM openjdk:11

ARG JAR_FILE=lib/api-trigger-1.0.jar

RUN DEBIAN_FRONTEND=noninteractive apt-get -y update
WORKDIR /usr/local/executer

COPY ${JAR_FILE} api-trigger-1.0.jar
ENTRYPOINT ["java","-jar","api-trigger-1.0.jar"]
