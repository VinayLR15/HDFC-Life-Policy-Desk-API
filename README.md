# HDFC Life Policy Desk API

A Spring Boot REST API for managing HDFC Life policies and claims.

The application stores REST policy data in an in-memory store, provides CRUD operations through REST APIs, documents the APIs using Springdoc OpenAPI, and manages the PostgreSQL-ready database schema using Flyway.

## Technologies Used

- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Validation
- Spring Data JPA (used for datasource auto-configuration only)
- H2 Database (development)
- PostgreSQL (production-ready configuration)
- Flyway
- Springdoc OpenAPI / Swagger UI
- Maven

> Note: The project is implemented using Spring Boot 4.1.1 and Java 21.

## Project Structure

```text
HDFC-Life-Policy-Desk-API/
│
├── pom.xml
├── README.md
├── .gitignore
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── hdfclife/
    │   │           └── config/
    │   │           ├── HdfcProperties.java
    │   │           ├── StoreLifecycle.java
    │   │           ├── DataSeeder.java
    │   │           └── RestExceptionHandler.java
    │   │
    │   │           ├── model/
    │   │           ├── store/
    │   │           ├── service/
    │   │           ├── web/
    │   │           └── exception/
    │   │
    │   └── resources/
    │       ├── application.yml
    │       ├── application-dev.yml
    │       ├── application-prod.yml
    │       └── db/
    │           └── migration/
    │               ├── V1__hdfc_life_schema.sql
    │               └── V2__seed_reference_riders.sql
    │
    └── test/
        └── java/
````

## Features

* Policy CRUD operations
* Policy filtering by status and type
* In-memory policy storage
* Claim creation and retrieval
* Claim amount validation
* Custom exception handling
* Constructor-based dependency injection
* Development and production profiles
* H2 database for development
* PostgreSQL-ready production configuration
* Flyway database migrations
* 3NF relational database schema
* Swagger UI and OpenAPI documentation
* Startup data seeding using `CommandLineRunner`

## Seed Data

The application loads the following six policies through `PolicyStore` during startup:

| Policy No      | Customer     | Type      | Base Premium | Status  |
| -------------- | ------------ | --------- | -----------: | ------- |
| HDFC-LIFE-1001 | Anita Sharma | TERM      |        18500 | Active  |
| HDFC-LIFE-1002 | Rahul Mehta  | ULIP      |        42000 | Active  |
| HDFC-LIFE-1003 | Priya Nair   | ENDOWMENT |        27000 | Lapsed  |
| HDFC-LIFE-1004 | Vikram Singh | TERM      |        15200 | Active  |
| HDFC-LIFE-1005 | Sneha Patel  | ULIP      |        36000 | Active  |
| HDFC-LIFE-1006 | Anita Sharma | ENDOWMENT |        22000 | Pending |

Startup statistics:

* Active policies: 4
* TERM policies: 2
* Unique customers: 5
* Maximum claim amount: 500000

## How to Run

### Prerequisites

* Java 21
* Maven 3.x or Maven Wrapper
* IntelliJ IDEA or any Java IDE

### Run using Maven Wrapper

```bash
./mvnw spring-boot:run
```

### Run using Maven

```bash
mvn spring-boot:run
```

### Build the project

```bash
./mvnw clean package
```

### Run the packaged JAR

```bash
java -jar target/HDFC-Life-Policy-Desk-API-0.0.1-SNAPSHOT.jar
```

The application starts on:

```text
http://localhost:8080
```

## Profiles

The default profile is `dev`.

### Development

The development profile uses an H2 in-memory database:

```text
jdbc:h2:mem:hdfclife;MODE=PostgreSQL;DB_CLOSE_DELAY=-1
```

H2 Console:

```text
http://localhost:8080/h2-console
```

Flyway is enabled in the development profile.

### Production

The production profile is configured for PostgreSQL.

Set the following environment variables:

```text
DB_URL
DB_USER
DB_PASSWORD
```

Example:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/hdfclife
export DB_USER=postgres
export DB_PASSWORD=your_password
```

Then run:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Do not commit real database passwords or secrets to GitHub.

## REST API Endpoints

Base URL:

```text
http://localhost:8080/api
```

| Method | Path                              | Description                               | Status Codes    |
| ------ | --------------------------------- | ----------------------------------------- | --------------- |
| GET    | `/api/policies`                   | Get all policies or filter by status/type | 200             |
| GET    | `/api/policies/{policyNo}`        | Get a policy by policy number             | 200 / 404       |
| POST   | `/api/policies`                   | Create a policy                           | 201 / 409       |
| PUT    | `/api/policies/{policyNo}`        | Replace an existing policy                | 200 / 404       |
| DELETE | `/api/policies/{policyNo}`        | Delete a policy                           | 204 / 404       |
| GET    | `/api/policies/{policyNo}/claims` | Get claims for a policy                   | 200 / 404       |
| POST   | `/api/claims`                     | File a claim                              | 201 / 400 / 404 |
| GET    | `/api/claims/{claimNo}`           | Get a claim by claim number               | 200 / 404       |
| GET    | `/api/policies?status=Active`     | Filter policies by status                 | 200             |
| GET    | `/api/policies?type=TERM`         | Filter policies by type                   | 200             |

## Example Policy JSON

```json
{
  "policyNo": "HDFC-LIFE-1004",
  "customer": "Vikram Singh",
  "type": "TERM",
  "basePremium": 15200,
  "status": "Active"
}
```

## Example Claim JSON

```json
{
  "claimNo": "CLM-01",
  "policyNo": "HDFC-LIFE-1001",
  "claimAmount": 25000,
  "urgency": "HIGH",
  "status": "SUBMITTED"
}
```

### Create Claim Request

For creating a claim, send:

```json
{
  "policyNo": "HDFC-LIFE-1001",
  "claimAmount": 25000,
  "urgency": "HIGH"
}
```

The application automatically generates the claim number and sets the status to `SUBMITTED`.

## API Examples

### Get All Policies

```http
GET /api/policies
```

Returns:

```json
[
  {
    "policyNo": "HDFC-LIFE-1001",
    "customer": "Anita Sharma",
    "type": "TERM",
    "basePremium": 18500,
    "status": "Active"
  }
]
```

### Get Policy by Number

```http
GET /api/policies/HDFC-LIFE-1004
```

### Filter by Status

```http
GET /api/policies?status=Active
```

### Filter by Type

```http
GET /api/policies?type=TERM
```

### Create Policy

```http
POST /api/policies
Content-Type: application/json
```

Request:

```json
{
  "policyNo": "HDFC-LIFE-1007",
  "customer": "Kiran Rao",
  "type": "TERM",
  "basePremium": 20000,
  "status": "Active"
}
```

Successful response:

```text
HTTP 201 Created
Location: /api/policies/HDFC-LIFE-1007
```

### Update Policy

```http
PUT /api/policies/HDFC-LIFE-1007
Content-Type: application/json
```

Request:

```json
{
  "policyNo": "HDFC-LIFE-1007",
  "customer": "Kiran Rao",
  "type": "TERM",
  "basePremium": 20000,
  "status": "Lapsed"
}
```

The policy number from the URL path is used.

### Delete Policy

```http
DELETE /api/policies/HDFC-LIFE-1007
```

Response:

```text
HTTP 204 No Content
```

### File a Claim

```http
POST /api/claims
Content-Type: application/json
```

Request:

```json
{
  "policyNo": "HDFC-LIFE-1001",
  "claimAmount": 25000,
  "urgency": "HIGH"
}
```

Successful response:

```text
HTTP 201 Created
Location: /api/claims/CLM-01
```

Response body:

```json
{
  "claimNo": "CLM-01",
  "policyNo": "HDFC-LIFE-1001",
  "claimAmount": 25000,
  "urgency": "HIGH",
  "status": "SUBMITTED"
}
```

## Validation and Exception Handling

The API uses custom runtime exceptions and a global `@RestControllerAdvice`.

### Exception Mapping

| Exception                  | HTTP Status |
| -------------------------- | ----------: |
| `PolicyNotFoundException`  |         404 |
| `ClaimNotFoundException`   |         404 |
| `DuplicatePolicyException` |         409 |
| `InvalidClaimException`    |         400 |

Error response format:

```json
{
  "error": "Policy not found: HDFC-LIFE-9999"
}
```

### Claim Validation

A claim is rejected when:

* `claimAmount <= 0`
* `claimAmount > 500000`
* The specified policy does not exist

Claim numbers are generated sequentially:

```text
CLM-01
CLM-02
CLM-03
```

## Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
PolicyStore Interface
    ↓
InMemoryPolicyStore
```

The controllers handle HTTP requests and responses.

The services contain business logic.

The `PolicyStore` interface abstracts policy storage.

`InMemoryPolicyStore` provides the in-memory implementation.

Dependency injection is performed using constructors.

No field injection is used.

## Flyway Database Migrations

Flyway owns the database schema.

Migration files:

```text
src/main/resources/db/migration/
├── V1__hdfc_life_schema.sql
└── V2__seed_reference_riders.sql
```

### V1

Creates the following five tables:

```text
customers
policies
claims
riders
policy_riders
```

### V2

Seeds exactly three reference riders:

```text
ACCIDENT_COVER     | Accident Cover
CRITICAL_ILLNESS   | Critical Illness
WAIVER_OF_PREMIUM  | Waiver of Premium
```

Policy seed data is intentionally not inserted through SQL. Policies are created by the Java `DataSeeder` using `PolicyStore.add(...)`.

## Entity Relationship

The database is designed in Third Normal Form (3NF).

```text
customers
    |
    | 1:N
    ↓
policies
    |
    | 1:N
    ↓
claims

policies
    |
    | N:M
    ↓
policy_riders
    |
    | N:1
    ↓
riders
```

### Foreign Keys

* `policies.customer_id` → `customers.id`
* `claims.policy_id` → `policies.id`
* `policy_riders.policy_id` → `policies.id`
* `policy_riders.rider_id` → `riders.id`

Customer name and email are stored only in the `customers` table.

Policies reference customers using `customer_id`.

Riders are stored separately and connected to policies through `policy_riders`.

## Swagger / OpenAPI

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON is available at:

```text
http://localhost:8080/v3/api-docs
```

The controllers are documented using:

* `@Tag`
* `@Operation`
* `@ApiResponse`
* `@Parameter`

Swagger UI was verified using:

```text
http://localhost:8080/swagger-ui/index.html
```

## Startup Output

On startup, the application prints:

```text
PolicyStore ready
Active profile → dev
Company name → HDFC Life
Max claim amount → 500000
Seeded policy count → 6
Lookup HDFC-LIFE-1004 customer → Vikram Singh
Active policy count via PolicyService → 4
TERM policy count via PolicyService → 2
Unique customer count → 5
Simple class name of injected PolicyStore → InMemoryPolicyStore
```

The lifecycle component also prints:

```text
PolicyStore shutdown
```

when the application is stopped.

## Why In-Memory Store vs PostgreSQL?

An in-memory store is suitable for development, demonstrations, prototypes, and test environments where persistence is not required.
For production use, PostgreSQL is preferable when policies and claims must survive application restarts and support multiple application instances.
An in-memory store is simple and fast, but its data is lost when the application stops.
Flyway provides versioned, repeatable, and controlled database migrations that can be reviewed and deployed consistently.
Unlike `ddl-auto=update`, Flyway explicitly manages schema changes through migration scripts and keeps a history of applied database versions.

## Testing

The project includes Spring Boot tests.

Run tests using:

```bash
./mvnw test
```

Build and test the complete project using:

```bash
./mvnw clean package
```

## Author

**Vinay L R**  
Backend Developer (HDFC Life)
