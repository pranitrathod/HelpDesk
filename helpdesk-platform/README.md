# HelpDesk Platform

This is the main Spring Boot module of the project.

The project is basically a support-ticket backend with **MySQL for data, Kafka for events, and external payment gateways for payments**.

## The main flow

```
API Request
    |
    v
Controller
    |
    v
DTO + Validation
    |
    v
Service
    |
    +----> Database (MySQL)
    |
    +----> Kafka
    |
    +----> Payment Gateway
    |
    v
API Response
```

Think of the layers like this:

**Controller** -> handles HTTP  
**DTO** -> request/response format  
**Service** -> business logic  
**Domain** -> ticket/payment state  
**Repository** -> database access  
**Kafka** -> events  
**Payment** -> Razorpay/Juspay integration  
**Exception** -> common error response

## Ticket creation

When a customer creates a ticket:

```
POST /api/v1/tickets
        |
        v
TicketController
        |
        v
CreateTicketRequest
        |
        v
TicketService
        |
        +--> find requester
        +--> calculate SLA
        +--> create ticket
        +--> save to MySQL
        +--> publish TicketEvent
        |
        v
TicketResponse
```

SLA depends on priority:

- CRITICAL -> 4 hours
- HIGH -> 8 hours
- MEDIUM -> 24 hours
- LOW -> 72 hours

## Ticket lifecycle

```
OPEN -> IN_PROGRESS -> WAITING_FOR_USER -> RESOLVED -> CLOSED
```

The service layer checks valid transitions, assignment rules and other ticket business rules.

## Comments

A ticket supports comments and replies.

```
Ticket
 ├── Comment
 │    ├── Reply
 │    └── Reply
 └── Comment
```

Replies use `parentCommentId`. The service checks that the parent comment belongs to the same ticket.

## Kafka

Ticket changes publish events to Kafka.

For example:

```
Ticket created
     |
     v
TicketEventPublisher
     |
     v
Kafka topic: ticket-events
```

This keeps event publishing separate from the controller and database code.

## Payments

Payment providers are hidden behind one interface:

```
PaymentGatewayClient
       |
       +---- RazorpayPaymentGatewayClient
       |
       +---- JuspayPaymentGatewayClient
```

The registry chooses the provider.

The checkout flow also uses `Idempotency-Key` to avoid duplicate payment transactions, and webhook signatures are verified before updating payment state.

Ticket payment follows:

```
PENDING -> AUTHORIZED -> REFUNDED
```

## Errors

Instead of writing try/catch in every controller:

```
Service exception
      |
      v
GlobalExceptionHandler
      |
      v
ApiError
```

So the API returns a consistent error response for validation, not-found, conflict, invalid ticket state and payment failures.

## Project structure

```
com.pranit.helpdesk

├── controller
├── dto
├── service
│   └── impl
├── domain
├── repository
├── event
├── payment
├── exception
└── config
```

Every DTO is a separate top-level class.

Examples:

```
CreateTicketRequest.java
TicketResponse.java
AddCommentRequest.java
CommentResponse.java
CreatePaymentRequest.java
PaymentResponse.java
```

Lombok handles repetitive boilerplate such as getters, setters and constructors.

## Tech stack

Java 17, Spring Boot 3.4, Spring Data JPA, MySQL, Kafka, Bean Validation, Lombok, Spring Actuator, Razorpay and Juspay.

## Run

```bash
cd helpdesk-platform
./mvnw spring-boot:run
```

JPA uses `ddl-auto: validate`, so the expected database schema must already exist.
