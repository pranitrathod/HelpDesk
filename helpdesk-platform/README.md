# HelpDesk Platform API

A portfolio-ready Spring Boot 3 service demonstrating a complete customer-support workflow: a customer opens a geolocated concern, an agent accepts it, conversations are maintained as threaded comments, and payment/refund actions are auditable domain events.

## Workflow highlights

- **SLA-aware lifecycle:** priority derives a 4h–72h SLA due time; tickets progress through `OPEN`, `IN_PROGRESS`, `WAITING_FOR_USER`, `RESOLVED`, and `CLOSED`.
- **Threaded collaboration:** comments can reply to a parent comment, while the service prevents cross-ticket replies.
- **Field-service location:** optional latitude, longitude, and human-readable address are persisted with the ticket for map clients.
- **Payment safety:** one payment per ticket, generated external-style reference, and a state-checked refund flow.
- **Event-ready design:** every business action publishes a `TicketEvent` to the `ticket-events` Kafka topic; consumers can independently implement notifications, audit persistence, and workflow automation.
- **API resilience:** Bean Validation plus a centralized exception handler returns consistent validation, not-found, and conflict responses.

## Representative endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `POST` | `/api/v1/tickets` | Create a concern (requires `X-User-Id`) |
| `PATCH` | `/api/v1/tickets/{id}/assignee` | Assign an agent |
| `PATCH` | `/api/v1/tickets/{id}/status` | Resolve/close and track lifecycle |
| `POST` | `/api/v1/tickets/{id}/comments` | Add a comment or reply using `parentCommentId` |
| `POST` | `/api/v1/tickets/{id}/payment` | Authorize ticket payment |
| `POST` | `/api/v1/tickets/{id}/payment/refund` | Refund an authorized payment |

Set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and `KAFKA_BOOTSTRAP_SERVERS` for each environment. The configuration deliberately uses environment variables so the same container image can be promoted through CI/CD and Azure deployment stages.
