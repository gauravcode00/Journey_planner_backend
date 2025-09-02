# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file from your build folder to the container
# IMPORTANT: Make sure this path and filename match the JAR you created in Step 2!
COPY target/Journey_Planner-0.0.1-SNAPSHOT.jar app.jar

# Tell Docker that the container listens on port 8080
EXPOSE 8080

# Run the JAR file when the container starts
ENTRYPOINT ["java","-jar","app.jar"]