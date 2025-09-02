# --- STAGE 1: Build the project with Maven ---
# Use a base image with Maven and JDK 17
FROM maven:3.8.5-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml file to download dependencies first
COPY pom.xml .

# Copy the Maven wrapper files
COPY .mvn/ .mvn
COPY mvnw .
COPY mvnw.cmd .

# Copy the rest of your application's source code
COPY src ./src

# Run the Maven package command to build the .jar file.
# -DskipTests speeds up the build by skipping tests.
RUN mvn package -DskipTests


# --- STAGE 2: Create the final, lightweight image ---
# Use a slim base image with only the Java Runtime Environment
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy ONLY the .jar file created in the 'builder' stage
COPY --from=builder /app/target/Journey_Planner-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]