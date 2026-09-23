# HelpDesk Backend

A Spring Boot backend for a customer-support system.

The main idea is simple:

A customer creates a ticket -> the backend applies business rules -> saves it in MySQL -> publishes an event to Kafka -> agents can work on the ticket -> payments can be handled when needed.

## How the request flows

```
Client
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
  +----> Domain
  |
  +----> Repository ---> MySQL
  |
  +----> Kafka
  |
  +----> Payment Gateway
  |
  v
Response
```

### What each layer does

- **Controller** - receives the API request and returns the response.
- **DTO** - defines what comes in and what goes out of the API.
- **Service** - contains the actual business logic.
- **Domain** - contains ticket/payment state and controlled state changes.
- **Repository** - talks to MySQL using Spring Data JPA.
- **Kafka** - publishes ticket events such as ticket creation, assignment and status changes.
- **Payment** - hides Razorpay/Juspay specific code behind a common interface.
- **Exception Handler** - converts exceptions into one consistent API error format.

## Example: create a ticket

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
        +--> load requester
        +--> calculate SLA
        +--> create Ticket
        +--> save in MySQL
        +--> publish event to Kafka
        |
        v
TicketResponse
```

SLA is based on priority:

| Priority | SLA |
|---|---:|
| CRITICAL | 4 hours |
| HIGH | 8 hours |
| MEDIUM | 24 hours |
| LOW | 72 hours |

## Ticket flow

```
OPEN
  -> IN_PROGRESS
  -> WAITING_FOR_USER
  -> RESOLVED
  -> CLOSED
```

The service checks whether a status change is valid and blocks operations that should not happen, such as modifying a closed ticket.

## Comments

Tickets support comments and replies.

```
Ticket
 ├── Comment
 │    ├── Reply
 │    └── Reply
 └── Comment
```

A reply uses `parentCommentId`, and the service verifies that the parent belongs to the same ticket.

## Payments

There are two payment pieces.

**Payment gateway integration**

```
PaymentService
     |
     v
PaymentGatewayRegistry
     |
     +---- Razorpay
     |
     +---- Juspay
```

The business code talks to `PaymentGatewayClient`, so it does not depend directly on one provider.

Checkout also uses an `Idempotency-Key` so the same request does not create duplicate transactions.

**Ticket payment**

```
PENDING -> AUTHORIZED -> REFUNDED
```

Refund is allowed only for an authorized payment.

## Exception handling

Controllers do not have repeated try/catch blocks.

```
Service
  |
  v
Exception
  |
  v
GlobalExceptionHandler
  |
  v
ApiError
```

So errors such as not-found, conflict, invalid ticket state, validation failure and payment failure are returned in a consistent format.

## Project structure

```
helpdesk-platform/src/main/java/com/pranit/helpdesk

├── controller    -> REST APIs
├── dto           -> request/response classes
├── service       -> business logic
├── domain        -> JPA entities + domain behavior
├── repository    -> database access
├── event         -> Kafka publishing
├── payment       -> payment gateway abstraction
├── exception     -> API errors + global handler
└── config        -> application/payment config
```

Each DTO is a separate class, for example:

```
CreateTicketRequest
TicketResponse
AddCommentRequest
CommentResponse
CreatePaymentRequest
PaymentResponse
```

Lombok is used to remove repetitive getters, setters and constructors. JPA entities intentionally use targeted Lombok annotations instead of `@Data`.

## Technologies used

- Java 17
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- MySQL
- Apache Kafka
- Bean Validation
- Lombok
- Spring Actuator
- Razorpay
- Juspay

## Main APIs

| Module | Base path |
|---|---|
| Tickets | `/api/v1/tickets` |
| Service Catalog | `/api/v1/service-catalog` |
| Knowledge Base | `/api/v1/knowledge-base` |
| Customer Feedback | `/api/v1/customer-feedback` |
| Payments | `/api/v1/payments` |

## Run locally

Prerequisites: Java 17, Maven, MySQL and Kafka.

```bash
cd helpdesk-platform
./mvnw spring-boot:run
```

The application reads DB, Kafka and payment settings from environment variables.
