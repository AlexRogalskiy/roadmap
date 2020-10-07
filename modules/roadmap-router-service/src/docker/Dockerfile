# Base OS layer
FROM openjdk:8-jdk-alpine

# Install curl package
RUN apk --no-cache add curl

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
ADD ${JAR_FILE} /opt/app/app.jar

# Set workdir inside container
WORKDIR /opt/app

# Expose service default port
EXPOSE 5000

# Run microservice process.
ENTRYPOINT [ "java", "-jar", "app.jar" ]
