FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} shortener-service.jar
ENTRYPOINT ["java","-jar","/shortener-service.jar"]
EXPOSE 8000