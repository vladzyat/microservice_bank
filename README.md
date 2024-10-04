# Task for Solva Project

## Overview
This is a Spring Boot application that provides services for currency exchange rate tracking. The project is packaged in a Docker container to make deployment easy and efficient.

## Prerequisites
Before running the project, ensure that you have the following software installed on your machine:

- [Java 17](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://docs.docker.com/get-docker/)

## Building and Running the Application in Docker

### 1. Clone the Repository
First, clone the repository to your local machine:

git clone https://github.com/your-username/task_for_solva.git
cd task_for_solva


2. Build the Spring Boot Application
You need to package the Spring Boot project into a JAR file using Maven. Run the following command to build the project:

./mvnw clean package
This will generate a .jar file in the target directory (e.g., task_for_solva-0.0.1-SNAPSHOT.jar).

3. Build the Docker Image
Next, build the Docker image for the application using the Dockerfile provided in the project root directory:

docker build -t task-for-solva-app .
This command will create a Docker image named task-for-solva-app.

4. Run the Docker Container
Now that the image is built, you can run it in a Docker container. Use the following command:

docker run -p 8080:8080 task-for-solva-app
This will:

Start the application in a Docker container.
Map port 8080 of your local machine to port 8080 inside the container.

5. Access the Application
Once the container is running, you can access the application by visiting:

http://localhost:8080
