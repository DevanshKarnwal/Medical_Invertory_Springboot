# Use a base image with Java
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy the JAR file into the image
COPY target/Medical-Inventory-Management-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the Spring Boot app runs on
EXPOSE 8080

# Run the JAR
CMD ["java", "-jar", "app.jar"]
