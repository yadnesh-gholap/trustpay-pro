# trustpay-pro
Escrow Platform built using Java, Spring Boot, PostgreSQL, Kafka, Redis and Docker.
## Local Development Setup

### Prerequisites
- Docker Desktop installed and running

### Infrastructure Setup
To spin up the local PostgreSQL database instance, navigate to the root directory and execute:
```bash
docker compose up -d

## Flyway Migration

Flyway automatically executes sequential SQL schema migrations during the application startup lifecycle.

* **Migration File Repository location:** `pro/src/main/resources/db/migration`
* **Naming Standard:** Use immutable version numbering sequences (e.g., `V1__init_schema.sql`, `V2__create_deal_table.sql`). 
* **Rule:** Never alter a migration script file once it has been integrated and executed on an environment.

## Logging

The application uses SLF4J with Logback.

Each request receives a correlation ID through the `X-Correlation-ID` header.

If the client does not send a correlation ID, the backend generates one automatically.

Example header:

```text
X-Correlation-ID: test-123