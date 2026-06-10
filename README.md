# HelpDesk Management Platform

HelpDesk Management Platform built using Spring Boot and modern backend engineering practices. The platform enables secure ticket lifecycle management while leveraging event-driven architecture, containerization, automated deployments, and cloud-native hosting.

## Architecture

Frontend Client

JWT Authentication & Authorization

Spring Boot REST APIs

Service Layer

MySQL Database

Event Processing:
Service Layer

Apache Kafka

Notification & Workflow Consumers

Deployment:
Docker Containers
↓
CI/CD Pipeline
↓
Microsoft Azure

## Key Capabilities

### Security

* JWT Authentication
* Role-Based Access Control (Admin, Agent, User)
* Spring Security Integration
* Protected REST Endpoints

### Ticket Management

* Ticket Creation
* Ticket Assignment
* Ticket Status Tracking
* Comment Management
* Audit-Friendly Workflow

### Event-Driven Processing
Client
   |
   v
Controller
   |
   v
Service
   |
   v
Database Save
   |
   v
ComplaintEventProducer
   |
   v
Kafka Topic
(complaint-created)
   |
   v
ComplaintEventConsumer
   |
   +----> Email Service
   |
   +----> Notification Service
   |
   +----> Audit Service
   s
* Ticket Created Events
* Ticket Assigned Events
* Ticket Updated Events
* Real-Time Kafka Event Consumption

### Reliability

* Global Exception Handling
* Request Validation
* Standardized API Responses
* Structured Logging

### Scalability

* Dockerized Services
* Kafka-Based Asynchronous Processing
* Stateless API Design
* Cloud Deployment on Azure

### DevOps

* Docker
* CI/CD Automation
* Azure Deployment
* Environment-Based Configuration

## Technology Stack

Backend

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA

Database

* MySQL

Messaging

* Apache Kafka

Cloud & DevOps

* Microsoft Azure
* Docker
* CI/CD Pipelines

Testing

* JUnit 5
* Mockito

Documentation

* Swagger / OpenAPI
