#!/bin/bash
FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} hash-service.jar
ENTRYPOINT ["java","-jar","/hash-service.jar"]
EXPOSE 8001