# RoomMatcher

RoomMatcher is a web application for managing users and room/flat listings, including authenticated user actions, listing interactions, image uploads, and favorites. The overall system is split into two independently developed repositories - one for the backend and one for the frontend - so each part can be maintained and deployed separately.

This repository (`roommatcher-api`) contains the REST API, business logic, security configuration, and database integration for RoomMatcher. It exposes endpoints for authentication, user profile management, listing management, favorites, and static file access for uploaded images.

## TVZ MC2 Context

This project was created as part of the TVZ MC2 student competition, organized by the Student Council of the Zagreb University of Applied Sciences (TVZ).

More information about the competition is available at: [https://mc2.tvz.hr/](https://mc2.tvz.hr/)

## Team Members

| Name              | Role      |
| ----------------- | --------- |
| Dino Stupar       | Backend   |
| Hrvoje Čučković   | Frontend  |
| Gabrijel Vlašiček | Marketing |

## Repositories

The RoomMatcher project is split into two separate repositories:

| Repository | Purpose | Tech |
| ---------- | ------- | ---- |
| [roommatcher-api](https://github.com/dinostupar78/room-matcher-api) | Backend REST API, authentication, database integration, and business logic | Spring Boot |
| [roommatcher-web](https://github.com/Hrvoje1307/room-matcher-web) | Frontend web application and user interface | Next.js |

## Architecture Overview

RoomMatcher is split into a frontend client, backend API, and relational database.

```text
roommatcher-web (Next.js)
        |
        | REST API
        v
roommatcher-api (Spring Boot)
        |
        v
PostgreSQL / H2
```

The frontend communicates with the backend through HTTP API calls. Authentication is handled using JWT access tokens.

---

# Backend - roommatcher-api

The backend provides a REST API secured with JWT-based authentication. It contains the service layer and persistence logic, integrates with relational databases, and includes configuration for both local development and production-like deployments.

## Tech Stack

| Category | Technology |
| -------- | ---------- |
| Language | Java 17 |
| Framework | Spring Boot 4.0.5 / Spring WebMVC |
| Build Tool | Maven / Maven Wrapper |
| Database (dev) | H2 in-memory database + H2 Console |
| Database (prod) | PostgreSQL |
| ORM / Persistence | Spring Data JPA / Hibernate |
| Security | Spring Security + JWT |
| JWT Library | JJWT 0.11.5 |
| API Documentation | springdoc-openapi / Swagger UI |
| Validation | Jakarta Bean Validation |
| Mapping | MapStruct |
| Containerization | Docker + Docker Compose |
| Configuration | `.env` support via `springboot4-dotenv` |
| Testing | Spring WebMVC Test |

## Features

- Authentication
    - User registration
    - Username/password login
    - JWT access token issuing
    - Refresh token workflow
    - Logout by invalidating refresh tokens

- User management
    - Read, update, and delete the current user profile
    - Public user lookup by id
    - Avatar upload using multipart requests

- Listings
    - Create, read, update, and delete listings
    - Fetch listings created by the authenticated user
    - Upload one or multiple listing images
    - Delete individual listing images

- Favorites
    - Add listings to favorites
    - Remove listings from favorites
    - Check whether a listing is favorited
    - Fetch the authenticated user’s favorite listings

- Static file serving
    - Uploaded files are served from the local `uploads/` directory under `/uploads/**`

## API Design

All API routes are defined under `/api/**`.

According to the security configuration, everything under `/api/**` requires authentication except routes under `/api/auth/**`.

### Auth (`/api/auth`)

| Method | Endpoint | Access | Description |
| ------ | -------- | ------ | ----------- |
| POST | `/api/auth/register` | Public | Register a new user account. |
| POST | `/api/auth/login` | Public | Authenticate with username/password and receive access + refresh token. |
| POST | `/api/auth/refresh` | Public | Exchange a valid refresh token for a new access token. |
| POST | `/api/auth/logout` | Public/Auth-dependent | Invalidate a refresh token. |

### Users (`/api/users`)

| Method | Endpoint | Access | Description |
| ------ | -------- | ------ | ----------- |
| GET | `/api/users` | Protected | Get a list of users. |
| GET | `/api/users/me` | Protected | Get the currently authenticated user profile. |
| PUT | `/api/users/me` | Protected | Update the current user profile. |
| DELETE | `/api/users/me` | Protected | Delete the current user account. |
| POST | `/api/users/me/avatar` | Protected | Upload or update the current user avatar. |
| GET | `/api/users/{id}` | Protected | Get a public user profile by id. |

### Listings (`/api/listings`)

| Method | Endpoint | Access | Description |
| ------ | -------- | ------ | ----------- |
| GET | `/api/listings` | Protected | Get all listings. |
| GET | `/api/listings/{id}` | Protected | Get listing details by id. |
| GET | `/api/listings/me` | Protected | Get listings created by the authenticated user. |
| POST | `/api/listings` | Protected | Create a new listing. |
| PUT | `/api/listings/{id}` | Protected | Update an existing listing. |
| DELETE | `/api/listings/{id}` | Protected | Delete a listing by id. |
| POST | `/api/listings/{id}/images` | Protected | Upload one or multiple images for a listing. |
| DELETE | `/api/listings/{id}/images/{imageId}` | Protected | Delete a specific listing image. |

### Favorites (`/api/favorites`)

| Method | Endpoint | Access | Description |
| ------ | -------- | ------ | ----------- |
| GET | `/api/favorites/me` | Protected | Get the authenticated user’s favorite listings. |
| GET | `/api/favorites/{listingId}/exists` | Protected | Check if the listing is in the authenticated user’s favorites. |
| POST | `/api/favorites/{listingId}` | Protected | Add a listing to favorites. |
| DELETE | `/api/favorites/{listingId}` | Protected | Remove a listing from favorites. |

## Authentication

RoomMatcher uses JWT-based authentication.

Public authentication routes are available under:

```text
/api/auth/**
```

Protected routes require a valid access token in the `Authorization` header:

```http
Authorization: Bearer <access_token>
```

If the token is missing, invalid, or expired, protected endpoints return `401 Unauthorized`.

## Project Structure

```text
roommatcher-api/
├── src/
│   ├── main/
│   │   ├── java/hr/tvz/roommatcher/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   ├── dto/
│   │   │   ├── security/
│   │   │   ├── config/
│   │   │   └── mapper/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       ├── schema.sql
│   │       └── data.sql
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

| Path | Description |
| ---- | ----------- |
| `controller/` | REST controllers and endpoint mappings |
| `service/` | Business logic |
| `repository/` | Spring Data JPA repositories |
| `model/` | JPA entities |
| `dto/` | Request and response DTOs |
| `security/` | Spring Security configuration, JWT filter, and token provider |
| `config/` | Application configuration classes |
| `mapper/` | MapStruct mappers |
| `application-*.properties` | Profile-based application configuration |
| `docker-compose.yml` | Local PostgreSQL container setup |
| `Dockerfile` | Docker image configuration for deployment |
| `uploads/` | Runtime directory for uploaded avatars and listing images |

## Installation & Running

### Prerequisites

- Java 17
- Docker + Docker Compose
- Maven or Maven Wrapper

### Clone the repository

```bash
git clone <backend-repository-url>
cd roommatcher-api
```

### Start PostgreSQL with Docker

This repository includes a `docker-compose.yml` file that starts PostgreSQL 16.

The compose file expects the following environment variables:

```env
POSTGRES_DB=roommatcher
POSTGRES_USER=roommatcher_user
POSTGRES_PASSWORD=your_password
```

Create a `.env` file in the repository root, then start the database:

```bash
docker compose up -d
```

PostgreSQL is published on host port `5433` and container port `5432`.

### Configure application properties

The application uses Spring profiles:

| Profile | Database | Description |
| ------- | -------- | ----------- |
| `dev` | H2 | Local in-memory development database |
| `prod` | PostgreSQL | Production-like configuration using environment variables |

### Required environment variables

For the `prod` profile, the following variables are required:

```env
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=jdbc:postgresql://localhost:5433/roommatcher
POSTGRES_USER=roommatcher_user
POSTGRES_PASSWORD=your_password
JWT_SECRET=your_secure_base64_secret
```

`JWT_SECRET` is required because the application reads the JWT signing secret from:

```properties
jwt.base64-secret=${JWT_SECRET}
```

## Running Locally

### Development profile with H2

Linux/macOS:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Windows:

```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production-like profile with PostgreSQL

Before running this profile, make sure PostgreSQL is running and the required environment variables are set.

Linux/macOS:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Windows:

```bash
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod
```

The backend should be available at:

```text
http://localhost:8080
```

## API Documentation

Swagger UI is available at:

```text
/swagger-ui/index.html
```

OpenAPI specification is available at:

```text
/v3/api-docs
```

For local development, the Swagger UI is typically available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Docker

The backend includes a `Dockerfile` for containerized deployment.

Build the Docker image:

```bash
docker build -t roommatcher-api .
```

Run the container:

```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_URL=jdbc:postgresql://host.docker.internal:5433/roommatcher \
  -e POSTGRES_USER=roommatcher_user \
  -e POSTGRES_PASSWORD=your_password \
  -e JWT_SECRET=your_secure_base64_secret \
  roommatcher-api
```

The Docker image uses Java 17.

## Deployment

The backend is deployed on Render as a Docker-based Web Service.

### Production URL

```text
https://room-matcher-api.onrender.com
```

### Render environment variables

The following environment variables must be configured on Render:

```env
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=jdbc:postgresql://<host>:5432/roommatcher
POSTGRES_USER=<database_user>
POSTGRES_PASSWORD=<database_password>
JWT_SECRET=<secure_base64_secret>
```

### Production database

The production database is hosted on Render PostgreSQL.

Important production notes:

- `DATABASE_URL` must use JDBC format: `jdbc:postgresql://host:5432/database`
- Do not use Render’s raw `postgres://...` URL directly in Spring Boot.
- Secrets must be configured through Render environment variables and must not be committed to Git.
- The deployed frontend origin must be allowed in the backend CORS configuration.
- CORS origins must not include a trailing slash.

Example CORS origin:

```text
https://room-matcher-web.vercel.app
```

## Notes
- All `/api/**` endpoints except `/api/auth/**` require a valid JWT access token.
- Uploaded files are served from the local `uploads/` directory under `/uploads/**`.
- Database initialization scripts (`schema.sql`, `data.sql`) are used in the `dev` profile with H2.
- The `prod` profile currently uses `spring.jpa.hibernate.ddl-auto=update` and disables SQL initialization.
- For a real production environment, Flyway or Liquibase migrations are recommended instead of relying on `ddl-auto=update`.
- The frontend is developed in a separate repository: `roommatcher-web`.