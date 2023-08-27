# Use the base image - used because we also want to build the app not just copy the apk from target folder.
FROM eclipse-temurin:17-jdk-focal

# Set the working directory
WORKDIR /app

# Copy Maven wrapper files and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download project dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Start the Spring Boot application
CMD ["./mvnw", "spring-boot:run"]