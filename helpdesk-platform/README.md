
# HelpDesk Platform

Spring Boot backend demonstrating a support-ticket workflow with MySQL, Kafka, payment-gateway abstraction, request validation, and centralized exception handling.

## 1. How the application works

    Client
      |
      v
    REST Controller
      |
      v
    DTO + Validation
      |
      v
    Service Layer
      |
      +----> Domain objects
      |
      +----> Repository ----> MySQL
      |
      +----> Event Publisher ----> Kafka
      |
      +----> Payment Gateway

Errors from the service/domain layer are translated by the GlobalExceptionHandler.

## 2. Ticket creation flow

    POST /api/v1/tickets
            |
            v
    TicketController
            |
            v
    CreateTicketRequest
            |
            | @Valid
            v
    TicketServiceImpl
            |
            +--> validate requester
            +--> calculate SLA
            +--> create Ticket
            +--> save Ticket
            +--> publish TicketEvent
            |
            +----> MySQL
            |
            +----> ticket-events
            |
            v
    TicketResponse

## 3. Ticket lifecycle

    OPEN -> IN_PROGRESS -> WAITING_FOR_USER -> RESOLVED -> CLOSED

The service layer validates transitions and prevents invalid state changes.

SLA is derived from priority:

| Priority | SLA |
|---|---:|
| CRITICAL | 4 hours |
| HIGH | 8 hours |
| MEDIUM | 24 hours |
| LOW | 72 hours |

## 4. Comments

A ticket can contain comments and threaded replies.

    Ticket
     ├── Comment
     │    ├── Reply
     │    └── Reply
     └── Comment

A reply uses parentCommentId.

The service validates that the parent comment belongs to the same ticket.

## 5. Payments

There are two payment-related flows.

### Gateway checkout

    PaymentController
          |
          v
    PaymentService
          |
          v
    PaymentGatewayRegistry
          |
          +---- Razorpay
          |
          +---- Juspay

The gateway abstraction keeps business logic independent of a specific provider.

Checkout uses Idempotency-Key.

Webhooks are signature-verified before transaction state is changed.

### Ticket payment

A ticket has one associated ticket payment.

    PENDING -> AUTHORIZED -> REFUNDED

Refund is allowed only for an authorized payment.

## 6. Exception handling

Instead of writing try/catch inside every controller:

    Service
      |
      +--> ResourceNotFoundException
      +--> ConflictException
      +--> InvalidTicketStateException
      +--> PaymentGatewayException
      |
      v
    GlobalExceptionHandler
      |
      v
    ApiError

Validation errors are also handled centrally and can include field-level messages.

## 7. Code structure

    src/main/java/com/pranit/helpdesk
    ├── controller      REST endpoints
    ├── dto             API request/response classes
    ├── domain          JPA entities + domain behavior
    ├── repository      Spring Data repositories
    ├── service         service interfaces
    │   └── impl        implementations
    ├── event           Kafka event publishing
    ├── payment         gateway abstraction and clients
    ├── exception       API errors and global handler
    └── config          application/payment configuration

## 8. Why the layers exist

Controller:
- Receives HTTP requests
- Validates request DTOs
- Returns HTTP responses
- Does not contain business rules

DTO:
- Defines the API contract
- Keeps API models separate from JPA entities

Service:
- Contains business rules
- Defines transaction boundaries
- Coordinates repositories and integrations

Domain:
- Represents persistent state
- Contains controlled state-changing methods such as assign, changeStatus, markRefunded

Repository:
- Handles database access
- Uses Spring Data JPA

Event:
- Publishes ticket-domain events without coupling the ticket service to consumers

Payment:
- Hides provider-specific logic behind PaymentGatewayClient

Exception:
- Converts application exceptions into consistent API responses

## 9. DTO convention

Every DTO is a separate top-level class.

Examples:

    dto/
    ├── CreateTicketRequest.java
    ├── TicketResponse.java
    ├── AddCommentRequest.java
    ├── CommentResponse.java
    ├── CreatePaymentRequest.java
    ├── PaymentResponse.java
    ├── CreateTicketPaymentRequest.java
    └── TicketPaymentResponse.java

Lombok removes repetitive getter, setter, and constructor code.

JPA entities use targeted Lombok annotations such as Getter and NoArgsConstructor rather than Data.

## 10. Technologies

- Java 17
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- MySQL
- Apache Kafka
- Spring Validation
- Lombok
- Spring Actuator
- Razorpay integration
- Juspay integration

## 11. Main APIs

| Module | Endpoint |
|---|---|
| Tickets | /api/v1/tickets |
| Service Catalog | /api/v1/service-catalog |
| Knowledge Base | /api/v1/knowledge-base |
| Customer Feedback | /api/v1/customer-feedback |
| Payments | /api/v1/payments |

## 12. Configuration

Environment variables:

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

JPA is configured with ddl-auto: validate, so the expected schema must already exist.

## 13. Run locally

Prerequisites:

- Java 17
- Maven
- MySQL
- Kafka

Run from the platform module:

    cd helpdesk-platform
    ./mvnw spring-boot:run

Windows:

    cd helpdesk-platform
    mvnw.cmd spring-boot:run
