# Use Maven to build the project
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom and source files
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# --------------------------

# Second stage: actual runtime image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
