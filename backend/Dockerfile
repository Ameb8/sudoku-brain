# Start from an official Java runtime image
FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY target/myapp.jar myapp.jar
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java", "-jar", "myapp.jar"]
