# DevHire

DevHire is a REST API for a developer recruitment platform. The current backend supports user registration, credential verification, and job management. It is built with Spring Boot and persists data in PostgreSQL.

This repository currently contains the backend only. Authentication tokens, authorization rules, and job application workflows are not implemented yet.

## Technologies

- Java 21
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA
- Spring Security
- Jakarta Bean Validation
- PostgreSQL
- BCrypt password hashing
- Maven

## Architecture

The application follows a layered architecture:

- **Controllers** expose the REST endpoints and validate incoming request bodies.
- **Services** contain user, login, and job business logic and map entities to response DTOs.
- **Repositories** use Spring Data JPA for database access.
- **Models** represent the `users` and `jobs` database entities.
- **DTOs** define API request and response payloads, keeping entity details out of the public contract.
- **Exception handling** centralizes validation, malformed request, duplicate email, and invalid credential responses.
- **Configuration** provides BCrypt password encoding and the current Spring Security filter chain.

## Implemented Features

- User registration with `CANDIDATE` or `COMPANY` roles
- Email format and required-field validation
- Duplicate email detection
- BCrypt password hashing before persistence
- Credential verification by email and password
- User responses that do not expose password hashes
- Create, list, retrieve, update, and delete job records
- Job types: `FULL_TIME`, `PART_TIME`, `INTERNSHIP`, and `CONTRACT`
- Job statuses: `OPEN` and `CLOSED`
- Consistent handling for validation errors, malformed JSON, duplicate emails, and invalid credentials

> The login endpoint currently verifies credentials and returns user data. It does not create a session or issue a token. All endpoints are currently public because authorization has not been implemented.

## API Endpoints

The API runs at `http://localhost:8080` by default.

### Users and authentication

| Method | Endpoint | Description | Success |
| --- | --- | --- | --- |
| `POST` | `/api/users` | Register a user | `200 OK` |
| `POST` | `/api/auth/login` | Verify email and password | `200 OK` |

#### Register a user

```http
POST /api/users
Content-Type: application/json
```

```json
{
  "name": "Alex Morgan",
  "email": "alex@example.com",
  "password": "change-me",
  "role": "CANDIDATE"
}
```

Successful response:

```json
{
  "id": 1,
  "name": "Alex Morgan",
  "email": "alex@example.com",
  "role": "CANDIDATE"
}
```

Allowed roles are `CANDIDATE` and `COMPANY`. Registering an existing email returns `409 Conflict`.

#### Verify credentials

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "email": "alex@example.com",
  "password": "change-me"
}
```

Valid credentials return the same user response shape shown above. Invalid credentials return `401 Unauthorized`.

### Jobs

| Method | Endpoint | Description | Success |
| --- | --- | --- | --- |
| `GET` | `/api/jobs` | List all jobs | `200 OK` |
| `POST` | `/api/jobs` | Create a job | `200 OK` |
| `GET` | `/api/jobs/{id}` | Retrieve a job by ID | `200 OK` |
| `PUT` | `/api/jobs/{id}` | Replace a job's current values | `200 OK` |
| `DELETE` | `/api/jobs/{id}` | Delete a job | `204 No Content` |

`GET`, `PUT`, and `DELETE` operations for an unknown job ID return `404 Not Found` where applicable.

#### Create or update a job

`POST /api/jobs` and `PUT /api/jobs/{id}` use the following request body:

```json
{
  "title": "Junior Java Developer",
  "description": "Build and maintain backend services.",
  "location": "São Paulo, Brazil",
  "type": "FULL_TIME",
  "status": "OPEN"
}
```

All fields are required. Allowed `type` values are `FULL_TIME`, `PART_TIME`, `INTERNSHIP`, and `CONTRACT`; allowed `status` values are `OPEN` and `CLOSED`.

A successful response includes the generated or existing job ID:

```json
{
  "id": 1,
  "title": "Junior Java Developer",
  "description": "Build and maintain backend services.",
  "location": "São Paulo, Brazil",
  "type": "FULL_TIME",
  "status": "OPEN"
}
```

### Error responses

Request validation errors return `400 Bad Request` as a field-to-message map:

```json
{
  "email": "Email must be valid",
  "password": "Password is required"
}
```

Malformed JSON or invalid enum values also return `400 Bad Request`:

```json
{
  "error": "Invalid request data"
}
```

## Project Structure

```text
src/main/
├── java/com/devhire/
│   ├── config/       # Security and password encoder configuration
│   ├── controller/   # REST controllers
│   ├── dto/          # Request and response contracts
│   ├── enums/        # User role, job type, and job status values
│   ├── exception/    # Custom exception and global API error handling
│   ├── model/        # JPA entities
│   ├── repository/   # Spring Data JPA repositories
│   ├── service/      # Business logic and DTO mapping
│   └── DevHireApplication.java
└── resources/
    └── application.properties
```

## Requirements

- JDK 21
- Maven 3.9 or later
- A running PostgreSQL instance

## Running Locally

1. Clone the repository and enter its directory:

   ```bash
   git clone <repository-url>
   cd DevHire
   ```

2. Create the PostgreSQL database expected by the application:

   ```sql
   CREATE DATABASE devhire;
   ```

3. Confirm that the database settings in `src/main/resources/application.properties` match your local environment. The current configuration uses:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/devhire
   spring.datasource.username=postgres
   spring.datasource.password=${DB_PASSWORD}
   ```

4. Set the `DB_PASSWORD` environment variable to the password of your PostgreSQL user.

   PowerShell:

   ```powershell
   $env:DB_PASSWORD="your-postgresql-password"
   ```

   Bash:

   ```bash
   export DB_PASSWORD="your-postgresql-password"
   ```

5. Start the application:

   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`. Hibernate is configured with `spring.jpa.hibernate.ddl-auto=update`, so the required tables are created or updated when the application starts.

## Roadmap

- JWT authentication
- Role-based authorization
- Job applications
- Automated tests
- Swagger/OpenAPI documentation
