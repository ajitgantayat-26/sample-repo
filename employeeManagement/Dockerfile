# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
# For Maven: target/*.jar | For Gradle: build/libs/*.jar
COPY target/*.jar app.jar

# Expose the port your Spring app runs on (usually 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
