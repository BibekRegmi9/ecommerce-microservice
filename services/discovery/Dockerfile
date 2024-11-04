# Use Amazon Corretto as the base image
FROM amazoncorretto:22

# Install Maven
RUN yum install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the project files to the container
COPY . /app

# Build the project
RUN mvn package

# Copy the built JAR file to the desired location
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} discoveryservice.jar

# Expose the port
EXPOSE 8761

# Define the command to run the JAR file
CMD ["java", "-jar", "discoveryservice.jar"]
