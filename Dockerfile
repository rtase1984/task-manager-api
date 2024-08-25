
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/tasks-mngr-api-0.0.1-SNAPSHOT.jar /app/tasks_mngr_api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/tasks_mngr_api.jar"]
