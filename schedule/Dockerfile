#!/bin/bash
FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} schedule-service.jar
ENTRYPOINT ["java","-jar","/schedule-service.jar"]
