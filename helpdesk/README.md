# HelpDesk Management Platform

Production-style HelpDesk REST API built with Java 21 and Spring Boot 3.5.3.

## Highlights
- JWT authentication with BCrypt password hashing
- Role-based access control: USER, AGENT, ADMIN
- Ticket lifecycle: OPEN -> ASSIGNED -> IN_PROGRESS -> RESOLVED -> CLOSED
- Ticket priority: LOW, MEDIUM, HIGH, CRITICAL
- Agent assignment and comments
- Agent/Admin dashboard summary
- JPA persistence
- Global API exception handling
- Actuator health endpoint for deployment
- Environment-based configuration
- Docker support
- GitHub Actions CI

## Architecture

```text
Client
  |
  v
REST Controllers
  |
  v
Services
  |
  v
Spring Data JPA Repositories
  |
  v
MySQL

Authentication: Client -> JWT Filter -> Spring Security -> Controller
```

## API

| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| POST | `/api/tickets` | Authenticated |
| GET | `/api/tickets/mine` | Authenticated |
| GET | `/api/tickets/assigned` | AGENT/ADMIN |
| GET | `/api/tickets/{id}` | Authenticated |
| PATCH | `/api/tickets/{id}` | Authenticated |
| POST | `/api/tickets/{id}/assign/{agent}` | ADMIN |
| POST | `/api/tickets/{id}/comments` | Authenticated |
| GET | `/api/tickets/{id}/comments` | Authenticated |
| GET | `/api/dashboard/summary` | AGENT/ADMIN |
| GET | `/actuator/health` | Public |

## Local setup

Requirements: Java 21, Maven, and MySQL 8+.

Configure environment variables:

```bash
DB_URL=jdbc:mysql://localhost:3306/helpdesk_db
DB_USERNAME=root
DB_PASSWORD=your-password
JWT_SECRET=your-random-secret-at-least-32-characters
```

Run:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

API starts on `http://localhost:8080`.

## Docker

```bash
./mvnw clean package -DskipTests
cd helpdesk
# or from this directory:
docker compose up --build
```

## CI

GitHub Actions runs a clean Maven build and test suite on pushes and pull requests.

## Showcase flow

1. Register a USER.
2. Login and copy the JWT.
3. Create a HIGH/CRITICAL ticket.
4. Login as ADMIN and assign it to an AGENT.
5. Login as the AGENT and move the ticket through its lifecycle.
6. Add comments and inspect the dashboard summary.
7. Demonstrate `/actuator/health` as the deployment health check.
