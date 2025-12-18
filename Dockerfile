# Multi-stage Dockerfile for Spring-Boot-App

# Builder stage: uses Maven to build the fat jar
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copy only the files required for dependency resolution first
COPY pom.xml .

# Copy sources
COPY src ./src

# Build the application (skip tests for faster image build)
RUN mvn -B -DskipTests package

# Runtime stage: slim JRE to run the app
FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/spring-boot-app-1.0.0.jar

WORKDIR /app
COPY --from=build /workspace/${JAR_FILE} /app/app.jar

# Expose default application port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
