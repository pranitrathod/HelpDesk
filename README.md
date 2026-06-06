# HelpDesk Management System

Enterprise-grade HelpDesk platform built using Spring Boot following modern backend engineering practices including JWT security, Kafka-based event processing, centralized exception handling, Docker containerization, and RESTful API design.

## Features

### Authentication & Authorization

* JWT-based Authentication
* Role-Based Access Control (USER, AGENT, ADMIN)
* Secure REST APIs using Spring Security

### Ticket Management

* Create Tickets
* Assign Tickets
* Update Ticket Status
* Add Comments
* Ticket History Tracking

### Event-Driven Architecture

Apache Kafka is used to publish and process ticket lifecycle events:

* Ticket Created
* Ticket Assigned
* Ticket Updated
* Ticket Resolved

### Exception Handling

Centralized exception handling using @RestControllerAdvice:

* Resource Not Found
* Validation Errors
* Unauthorized Access
* Business Exceptions
* Internal Server Errors

### Technology Stack

Backend:

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA
* MySQL

Messaging:

* Apache Kafka

DevOps:

* Docker
* Docker Compose
* Kubernetes

Testing:

* JUnit 5
* Mockito

Documentation:

* Swagger/OpenAPI

## Architecture

Client
↓
Spring Security (JWT)
↓
Controller Layer
↓
Service Layer
↓
Repository Layer
↓
MySQL

Service Layer
↓
Kafka Producer
↓
Kafka Topics
↓
Kafka Consumers

## Future Enhancements

* Email Notifications
* SLA Monitoring
* Audit Logging
* Elasticsearch Integration
* Redis Caching
* Payment Integration for Premium Support Plans
* Microservices Migration

## Key Engineering Concepts Demonstrated

* Clean Architecture
* Layered Design
* Event-Driven Systems
* Secure API Development
* Distributed Messaging
* Containerization
* CI/CD Readiness

