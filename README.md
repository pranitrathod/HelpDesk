
# HelpDesk Backend Platform

A Spring Boot backend for a customer-support/helpdesk system.

The primary implementation is the helpdesk-platform module. It demonstrates how a support ticket moves through its lifecycle, how business rules are separated from HTTP handling, how data is persisted in MySQL, how domain events are published to Kafka, how payments are isolated behind gateway interfaces, and how API errors are handled consistently.

## What does this system do?

A customer creates a support ticket.

The backend then:

1. Validates the request.
2. Creates the ticket.
3. Calculates an SLA deadline from ticket priority.
4. Persists the ticket in MySQL.
5. Publishes a TicketEvent to Kafka.
6. Allows assignment and status changes.
7. Allows threaded comments and replies.
8. Supports ticket payments and refunds.
9. Returns either a successful response or a centralized API error.

Additional modules provide:

- Service catalog
- Knowledge-base articles
- Customer feedback / CSAT
- Payment checkout and webhooks

## Request flow

    Client
      |
      v
    Controller
      |
      v
    DTO + Bean Validation
      |
      v
    Service
      |
      +------------------+
      |                  |
      v                  v
    Domain           Repository
      |                  |
      |                  v
      |                MySQL
      |
      +----> Kafka events
      |
      +----> Payment Gateway

Business exceptions are handled centrally:

    Service / Domain
          |
          v
    GlobalExceptionHandler
          |
          v
       ApiError

## Example: creating a ticket

Request:

    POST /api/v1/tickets
    X-User-Id: 101

    {
      "title": "Laptop is not connecting to VPN",
      "description": "VPN fails after entering corporate credentials",
      "priority": "HIGH"
    }

Internal flow:

    TicketController
          |
          v
    CreateTicketRequest
          |
          | @Valid
          v
    TicketServiceImpl
          |
          +--> load requester
          +--> calculate SLA
          +--> create Ticket
          +--> save ticket
          +--> publish TicketEvent
          |
          +------> MySQL
          |
          +------> Kafka topic: ticket-events
          |
          v
    TicketResponse

SLA rules:

| Priority | SLA |
|---|---:|
| CRITICAL | 4 hours |
| HIGH | 8 hours |
| MEDIUM | 24 hours |
| LOW | 72 hours |

## Ticket lifecycle

    OPEN
      |
      v
    IN_PROGRESS
      |
      v
    WAITING_FOR_USER
      |
      v
    RESOLVED
      |
      v
    CLOSED

The service layer validates state transitions and prevents invalid operations such as modifying a closed ticket.

## Comments

Comments support threaded replies:

    Ticket
     ├── Comment 1
     │     ├── Reply 1
     │     └── Reply 2
     └── Comment 2

A reply contains parentCommentId.

The service verifies that the parent comment belongs to the same ticket before creating the reply.

## Payment design

Payment integration uses an interface-based design:

    PaymentGatewayClient
            |
            +---- RazorpayPaymentGatewayClient
            |
            +---- JuspayPaymentGatewayClient

PaymentGatewayRegistry selects the implementation.

Checkout flow:

    PaymentController
          |
          v
    PaymentService
          |
          v
    PaymentGatewayRegistry
          |
          v
    PaymentGatewayClient
          |
          v
    External payment provider

The checkout endpoint uses Idempotency-Key so a repeated request can return the existing transaction instead of creating another one.

Webhooks are signature-verified before the payment transaction is updated.

## Exception handling

Controllers do not contain repetitive try/catch blocks.

Instead:

    Controller
        |
        v
    Service
        |
        +--> ResourceNotFoundException
        +--> ConflictException
        +--> InvalidTicketStateException
        +--> PaymentGatewayException
        |
        v
    @RestControllerAdvice
        |
        v
    ApiError

Example error:

    {
      "timestamp": "2026-09-23T18:00:00Z",
      "status": 404,
      "code": "RESOURCE_NOT_FOUND",
      "message": "Ticket with identifier 42 was not found"
    }

## Project structure

    helpdesk-platform/
    └── src/main/java/com/pranit/helpdesk
        ├── controller
        ├── dto
        ├── domain
        ├── repository
        ├── service
        │   └── impl
        ├── event
        ├── payment
        ├── exception
        └── config

Read the project in this order when reviewing the code:

    Controller
        -> DTO
        -> Service
        -> Domain / Repository
        -> Event / Payment integration
        -> Response

## Technologies

Backend:
- Java 17
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- Bean Validation
- Lombok

Database:
- MySQL

Messaging:
- Apache Kafka
- Spring Kafka

Payments:
- Razorpay
- Juspay
- Idempotent checkout
- Webhook signature verification

Operations:
- Spring Actuator
- Environment-based configuration
- Docker-ready configuration

## Main APIs

| Module | Base endpoint |
|---|---|
| Tickets | /api/v1/tickets |
| Service Catalog | /api/v1/service-catalog |
| Knowledge Base | /api/v1/knowledge-base |
| Customer Feedback | /api/v1/customer-feedback |
| Payments | /api/v1/payments |

### Ticket APIs

| Method | Endpoint | Purpose |
|---|---|---|
| POST | /api/v1/tickets | Create ticket |
| GET | /api/v1/tickets/{ticketId} | Get ticket |
| GET | /api/v1/tickets | List tickets |
| PATCH | /api/v1/tickets/{ticketId}/assignee | Assign ticket |
| PATCH | /api/v1/tickets/{ticketId}/status | Change status |
| POST | /api/v1/tickets/{ticketId}/comments | Add comment/reply |
| GET | /api/v1/tickets/{ticketId}/comments | List comments |
| POST | /api/v1/tickets/{ticketId}/payment | Create ticket payment |
| POST | /api/v1/tickets/{ticketId}/payment/refund | Refund payment |

## Configuration

The application uses environment variables:

    DB_URL
    DB_USERNAME
    DB_PASSWORD
    KAFKA_BOOTSTRAP_SERVERS

    RAZORPAY_BASE_URL
    RAZORPAY_KEY_ID
    RAZORPAY_KEY_SECRET

    JUSPAY_BASE_URL
    JUSPAY_MERCHANT_ID
    JUSPAY_API_KEY

JPA uses ddl-auto: validate, so the expected database schema must already exist.

## Run locally

Prerequisites:

- Java 17
- Maven
- MySQL
- Kafka

Run:

    cd helpdesk-platform
    ./mvnw spring-boot:run

Windows:

    cd helpdesk-platform
    mvnw.cmd spring-boot:run

## What this project demonstrates

- Layered Spring Boot architecture
- REST API design
- DTO/entity separation
- Bean Validation
- Transaction boundaries
- JPA relationships
- Domain state transitions
- Global exception handling
- Kafka event publishing
- Payment gateway abstraction
- Idempotency
- Webhook verification
- Environment-based configuration

The important idea is the flow:

    HTTP request
       -> validation
       -> business logic
       -> database / external integration
       -> event publication
       -> API response
