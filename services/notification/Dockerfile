# Use Amazon Corretto as the base image
FROM amazoncorretto:22

# Install Maven
RUN yum install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the project files to the container
COPY . /app

# Build the project
RUN mvn clean package -DskipTests

# Copy the built JAR file to the desired location
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notificationservice.jar

# Expose the port
EXPOSE 8040

# Define the command to run the JAR file
CMD ["java", "-jar", "notificationservice.jar"]
