# eCommerce Microservices Project

This project demonstrates a basic eCommerce system built using a microservices architecture in Java Spring Boot. The system includes services for customer management, orders, payments, and notifications, and showcases several advanced features like service discovery, API gateways, asynchronous messaging, and distributed tracing.

## Project Structure

The project consists of the following microservices:

1. **Customer Service**: Handles customer data.
2. **Order Service**: Manages orders placed by customers.
3. **Payment Service**: Processes payments for orders.
4. **Notification Service**: Sends notifications (via email) when certain events occur (e.g., order placement).

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Microservices Setup](#microservices-setup)
   - [Customer Service](#customer-service)
   - [Order Service](#order-service)
   - [Payment Service](#payment-service)
   - [Notification Service](#notification-service)
3. [Microservices Communication](#microservices-communication)
4. [Service Discovery with Eureka](#service-discovery-with-eureka)
5. [API Gateway](#api-gateway)
6. [Asynchronous Communication with RabbitMQ](#asynchronous-communication-with-rabbitmq)
7. [Distributed Tracing](#distributed-tracing)
8. [Additional Architecture Details](#additional-architecture-details)

---

## Prerequisites
- Java 17+
- Docker
- Docker Compose
- Maven
- Kafka

---

## Microservices Setup

### 1. Customer Service
- A microservice responsible for managing customer data.
- Exposes REST APIs for adding, updating, and querying customer information.
  
#### Key Technologies:
- Spring Boot
- Spring Data JPA (for persistence)
- Postgres and mongodb(as the database)

### 2. Order Service
- Manages customer orders and keeps track of their status.
  
#### Key Technologies:
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Kafka (for event-driven communication)

### 3. Payment Service
- Handles payment processing for orders.
  
#### Key Technologies:
- Spring Boot
- Spring Data JPA
- Kafka

### 4. Notification Service
- Sends email notifications when an order is placed.

#### Key Technologies:
- Spring Boot
- JavaMail
- Kafka

---

## Microservices Communication

### 1. REST Communication via HTTP
- **RestTemplate** and **OpenFeign** are used for synchronous inter-service communication.
  
### 2. Comparing RestTemplate and OpenFeign:
- **RestTemplate**: Simple to use for synchronous HTTP calls but requires manual configuration.
- **OpenFeign**: More declarative and integrates seamlessly with Eureka and Ribbon for load balancing.

### 3. Testing Communication:
- RestTemplate and OpenFeign communication between Customer, Order, and Payment services can be tested using Postman or integration tests.

---

## Service Discovery with Eureka
- **Eureka Server** is used for service discovery, allowing microservices to register themselves at runtime.
- **Discovery-Service**: Created using Spring Cloud Netflix Eureka.
  
### Key Steps:
- Externalize configurations using Spring Cloud Config.
- Register Customer, Order, Payment, and Notification services with Eureka.

---

## API Gateway
- **Spring Cloud Gateway** is used as the API Gateway.
- Routes all external requests to the respective microservices and applies security, rate-limiting, and routing logic.

---

## Asynchronous Communication with RabbitMQ

### 1. Setup RabbitMQ:
- Docker-compose is used to set up RabbitMQ.
- Kafka enables asynchronous communication between services (e.g., Order and Notification services).

### 2. Concepts:
- **Queue**: A place where messages are stored.
- **Exchange Types**: Direct, Topic, Fanout, and Headers.
  
### 3. Order Placement Workflow:
- Once an order is placed, a message is published to the **order-queue**.
- **Notification Service** consumes this message and sends an email confirmation to the customer.

---

## Notification Service & JavaMail

### 1. Email Notifications:
- **MailDev** is used in Docker to simulate email sending.
- **JavaMail** is used in the Notification Service to send order confirmation emails.

### 2. Message Consumption:
- The Notification Service consumes messages from the **order-queue** in Kafka and sends the corresponding emails.

### 3. Persistence:
- Notification details are saved to a database for future reference.

---

## Distributed Tracing

### 1. Sleuth and Zipkin:
- **Spring Cloud Sleuth** is used to trace requests as they propagate through different microservices.
- **Zipkin** is used to visualize the trace data.

### 2. Zipkin Setup:
- A **Zipkin container** is added via Docker Compose.
- The Zipkin dashboard provides insights into the performance of each service.

---

## Additional Architecture Details

### Service Registry and Discovery (/)
- Eureka is used for service discovery.

### API Gateway (/)
- Zuul or Spring Cloud Gateway is used for routing requests to the appropriate microservices.

### CQRS (Command Query Responsibility Segregation) (/)
- Command and Query operations are separated in the Order Service.

### Database per Service (/)
- Each microservice has its own dedicated PostgreSQL database.

### Event-Driven Architecture (RabbitMQ) (/)
- Kafka is used to facilitate event-driven communication between services.

### Saga Pattern (x)
- **Not implemented**: For managing distributed transactions across services.

### Circuit Breaker (x)
- **Not implemented**: For handling failures in microservices gracefully.
