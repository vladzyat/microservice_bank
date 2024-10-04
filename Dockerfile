# Use a base image with Java runtime
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from target folder to the container
COPY target/task_for_solva-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application will run on (default for Spring Boot is 8080)
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

