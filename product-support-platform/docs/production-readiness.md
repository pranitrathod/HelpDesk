# Production-readiness checklist

This repository demonstrates robust patterns; do not put it on a real public endpoint until the following work is completed and reviewed.

- [ ] Replace demo HS256 shared-secret token issuance with a managed OIDC provider / asymmetric keys, key rotation, refresh-token and revocation policy, MFA/admin hardening, and proper account lifecycle.
- [ ] Store secrets in a cloud secrets manager; rotate JWT, DB, and payment credentials. Never use `.env.example` values in a real environment.
- [ ] Make core and MySQL private, enforce TLS end to end, configure restrictive network policy and service authentication/mTLS between BFF and core.
- [ ] Add rate limiting, brute-force detection, account lockout policy, CSRF/CORS policy aligned to the client, request-size limits, security headers, and audit logging.
- [ ] Execute schema migration tests, integration tests with disposable MySQL/Kafka, concurrency tests for idempotency, and API contract tests in CI. Run `mvn verify` on the target environment.
- [ ] Load test and tune DB pools, HTTP pools, Kafka partitions/consumer concurrency, thread counts, JVM memory, query plans and index selection.
- [ ] Configure Kafka TLS/SASL, authorization ACLs, replication factor, retention, topic provisioning, consumer lag alerting, DLQ policy and replay procedure.
- [ ] Harden outbox claiming for multiple replicas (leases/row locks), backoff with `next_attempt_at`, dead-letter operations, and reconciliation of events that are repeatedly failing.
- [ ] Add a retention/cleanup policy for expired idempotency keys and processed-event records; retain audit and payment records for the legally/operationally required period.
- [ ] Implement a real notification provider with delivery records, bounded retry, provider idempotency and suppression/consent preferences. The current worker logs a simulated notification.
- [ ] Implement payment initiation reconciliation for provider order creation timeouts/orphan orders, webhook replay/idempotency, refund handling, quote expiry and settlement reports. Verify provider account capture settings and test live flows in a sandbox.
- [ ] Add privacy and retention rules for customer data, ticket attachments, payment records and audit history; do not store card PAN/CVV or sensitive payment credentials.
- [ ] Configure backups, point-in-time recovery, disaster recovery objectives and a restore drill.
- [ ] Set SLOs, metrics dashboards, traces, alerts, PII-safe logs and operational runbooks. Monitor request latency, 4xx/5xx rates, database saturation, outbox age, Kafka lag, payment pending age and webhook failures.
- [ ] Add deployment manifests, rolling/blue-green release strategy, readiness/liveness probes, graceful shutdown and rollback procedures.
- [ ] Perform threat modeling, dependency scanning, SAST/DAST, penetration testing, license review and code review.

## Important caveat

A payment provider integration is not production-complete merely because an order can be created. Real launch requires merchant configuration, webhook endpoint configuration, reconciliation, refunds/chargeback handling, security review and payment provider sandbox tests.
