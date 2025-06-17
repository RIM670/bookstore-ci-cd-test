# Use lightweight Java runtime image
FROM eclipse-temurin:18-jdk-alpine

# Set the directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/*.jar app.jar

# Open port 8080 (Spring Boot default)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
