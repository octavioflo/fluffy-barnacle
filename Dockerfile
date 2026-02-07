# Stage 1: Build the application using the Chainguard Gradle image
FROM cgr.dev/chainguard/gradle:latest AS builder
#ENV GRADLE_USER_HOME=/tmp/gradle
USER root
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

# Stage 2: Create the final, minimal image using the Chainguard JRE image
FROM cgr.dev/chainguard/jre:latest
LABEL authors="octavioflores"
WORKDIR /app
# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]