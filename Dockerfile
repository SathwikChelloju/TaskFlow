# Use Java 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Give execute permission to Maven Wrapper
RUN chmod +x mvnw

# Build the Spring Boot project
RUN ./mvnw clean package -DskipTests

# Render provides the PORT environment variable
ENV PORT=10000

# Expose the port
EXPOSE 10000

# Start the application
CMD ["java", "-jar", "target/taskflow-0.0.1-SNAPSHOT.jar"]