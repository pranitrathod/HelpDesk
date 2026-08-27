# HelpDesk Management Platform

A production-oriented HelpDesk REST API built with Java 21 and Spring Boot. The project demonstrates secure API design, layered architecture, relational persistence, validation, centralized error handling, containerization, and CI automation.

## What this project demonstrates

- RESTful ticket management
- User and ticket domain modeling with Spring Data JPA
- Layered Controller → Service → Repository architecture
- JWT-based authentication foundation
- Request validation and centralized exception handling
- MySQL persistence
- Health endpoints through Spring Boot Actuator
- Docker image and local Docker Compose environment
- GitHub Actions CI with Java 21 and Maven
- Environment-variable based runtime configuration
- Kafka/event-driven components retained for asynchronous workflow expansion

## Architecture

```text
Client
  |
  v
Spring Boot REST API
  |
  +--> Controller
  |
  +--> Service
  |
  +--> Repository
  |       |
  |       v
  |     MySQL
  |
  +--> Event layer / Kafka
          |
          +--> asynchronous notification/workflow consumers
```

## Local development

### Requirements

- Java 21
- Maven (or included Maven Wrapper)
- MySQL 8+
- Docker Desktop (optional)

### Configuration

Never commit credentials. Configure these environment variables when deploying:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
DDL_AUTO
JWT_SECRET
JWT_EXPIRATION
```

For local development, the application defaults to a local MySQL database named `helpdesk_db`.

### Run with Maven

```bash
cd helpdesk
./mvnw test
./mvnw spring-boot:run
```

Windows:

```bat
cd helpdesk
mvnw.cmd test
mvnw.cmd spring-boot:run
```

### Run with Docker Compose

```bash
cd helpdesk
./mvnw clean package -DskipTests

docker compose up --build
```

The API is then available on port `8080`.

## API areas

| Area | Purpose |
|---|---|
| Authentication | Registration/login foundation |
| Users | User management |
| Tickets | Create, read, update and delete tickets |
| Comments | Ticket comments |
| Complaints | Complaint workflow |
| Health | Application health for deployment platforms |

## CI

Every push to the main development branches and pull requests targeting `main` run the Maven test suite with Java 21 through GitHub Actions.

## Security notes

- Secrets are supplied through environment variables rather than source control.
- Use a long, randomly generated `JWT_SECRET` in deployed environments.
- The database password that existed in earlier repository history should be rotated because Git history is immutable from the application configuration alone.
- Do not use the sample Docker Compose credentials in production.

## Deployment target

The application is designed to be deployable as a Dockerized Spring Boot service with a managed MySQL/PostgreSQL-compatible database. A free-tier deployment can be used for portfolio demonstration, subject to the provider's current limits.

## Interview discussion points

Be prepared to explain:

1. Why Controller/Service/Repository layers are separated.
2. How JWT authentication works from login through request filtering.
3. Why secrets belong in environment variables.
4. Why database migrations are preferable to relying on `ddl-auto=update` for production.
5. When Kafka is useful and when synchronous processing is simpler.
6. How Docker makes the application environment reproducible.
7. How CI catches compilation/test regressions before deployment.
8. How the ticket lifecycle can evolve into SLA, assignment, audit and notification workflows.
