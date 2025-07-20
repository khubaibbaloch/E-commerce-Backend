# Use Java 24 base image
FROM eclipse-temurin:24-jdk

WORKDIR /app
COPY build/libs/backend-all.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
