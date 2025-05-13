# Use official Java runtime as a base image
FROM eclipse-temurin:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy Maven/Gradle files first for better caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
# If using Gradle, replace with:
# COPY gradlew .
# COPY gradle gradle
# COPY build.gradle .

# Download dependencies (this layer will be cached unless pom.xml/build.gradle changes)
RUN ./mvnw dependency:go-offline -B
# If using Gradle, replace with:
# RUN ./gradlew dependencies

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests
# If using Gradle, replace with:
# RUN ./gradlew build -x test

# Use a smaller base image for runtime
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=0 /app/target/*.jar app.jar

# Set the startup command
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Expose the port the app runs on
EXPOSE 8080