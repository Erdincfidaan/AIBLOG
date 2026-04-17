# AIBlog REST API

## Overview
AIBlog is a Spring Boot REST API for a blog platform. It provides JWT-based authentication and role-based authorization, email verification via SMTP, CRUD operations for posts, categories, and comments, and an integration with the Gemini API for AI-assisted content generation. The service persists data in PostgreSQL using Spring Data JPA, uses MapStruct for DTO mapping, and centralizes error handling with a global exception handler. The project is containerized with Docker Compose for reproducible local environments.

## Features
- **Authentication**
  - User registration and login
  - JWT token issuance and validation
  - Stateless security configuration
- **Email verification (SMTP)**
  - Verification token generation
  - Verification link flow (`/api/auth/verifyemail`)
- **Blog domain APIs**
  - Posts: create/update/delete and read endpoints
  - Categories: administrative management endpoints
  - Comments: create/update/delete endpoints
- **AI integration**
  - Gemini-backed content generation endpoint
  - Admin endpoint to create a post from generated content
- **Persistence**
  - PostgreSQL database
  - Spring Data JPA repositories
  - Schema support via Hibernate default schema configuration
- **Developer experience**
  - MapStruct DTO mapping
  - Global exception handling
  - Dockerfile + Docker Compose workflow
  - Secrets externalized via `.env` (not committed)

## Architecture / Design Approach
The codebase follows a layered architecture:
- **Controller layer**: HTTP request/response handling and request validation.
- **Service layer**: business logic and authorization checks that are not purely HTTP concerns.
- **Repository layer**: persistence operations using Spring Data JPA.
- **Domain layer**: JPA entities representing core business objects.
- **Cross-cutting concerns**:
  - **Security**: JWT filter + Spring Security configuration.
  - **Mapping**: MapStruct mappers between DTOs and entities.
  - **Error handling**: global exception handler producing consistent API errors.
  - **External integration**: Gemini client for AI content generation.

## Tech Stack
- **Language / Runtime**
  - Java 21
- **Frameworks**
  - Spring Boot (REST API, Security, Validation, Mail)
- **Security**
  - JWT (Bearer token)
- **Persistence**
  - PostgreSQL
  - Spring Data JPA / Hibernate
- **Mapping**
  - MapStruct
- **Build**
  - Maven
- **Containerization**
  - Docker, Docker Compose
- **External Services**
  - Gemini API (Google GenAI)

## Project Structure
Key paths under `src/main/java/com/Blog/AIBlog/`:
- `Controller/` and `Controller/Impl/`: REST controllers
- `Service/` and `Service/Impl/`: business logic
- `Repository/`: Spring Data repositories
- `Entity/`: JPA entities (`User`, `Post`, `Comment`, `Category`)
- `Dto/request/`, `Dto/response/`: request/response models
- `mapper/`: MapStruct mappers
- `config/`: security configuration and Gemini client
- `filter/`: JWT filter
- `util/`: JWT utility helpers
- `exception/`: global exception handling and custom exceptions

Repository root:
- `Dockerfile`: container image definition
- `docker-compose.yml`: local stack (API + PostgreSQL)
- `.env.example`: environment variable template (copy to `.env`)
- `README.md`: documentation

## Configuration
This project is configured to **load secrets and environment-specific settings from environment variables**. In Docker Compose mode, these are provided via a local `.env` file.

### `.env` usage
1. Copy `.env.example` to `.env` in the repository root.
2. Replace `CHANGE_ME` values with your own credentials/keys.
3. Do not commit `.env` (it is ignored via `.gitignore`).

### Environment variables
The application reads these variables (directly or via placeholders in `application.properties`):

- **Database**
  - `SPRING_DATASOURCE_URL` (Docker Compose: `jdbc:postgresql://db:5432/postgres`)
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
- **Hibernate / JPA**
  - `HIBERNATE_DEFAULT_SCHEMA` (default: `ai_blog`)
  - `HIBERNATE_DDL_AUTO` (default: `update`)
  - `HIBERNATE_TIME_ZONE` (default: `Europe/Istanbul`)
- **JWT**
  - `JWT_SECRET` (must be Base64; the service decodes it to build the signing key)
  - `JWT_EXP` (default: `86400000` milliseconds)
- **Gemini**
  - `GEMINI_API_KEY`
- **Mail (SMTP)**
  - `SPRING_MAIL_HOST` (default: `smtp.gmail.com`)
  - `SPRING_MAIL_PORT` (default: `587`)
  - `SPRING_MAIL_USERNAME`
  - `SPRING_MAIL_PASSWORD`
- **Application**
  - `APP_BASE_URL` (default: `http://localhost:8080/api/auth`)

## Installation & Running

### Docker setup (recommended)
**Prerequisites**
- Docker Desktop

**Steps**
1. Create `.env`:

```powershell
copy .env.example .env
```

2. Edit `.env` and set required values.

3. Build and run:

```powershell
docker compose up --build
```

**Service URL**
- API: `http://localhost:8080`

**Stop**
```powershell
docker compose down
```

### Local setup (without Docker)
**Prerequisites**
- JDK 21
- PostgreSQL accessible locally or remotely
- Environment variables set in your shell (see Configuration section)

**Run**
```powershell
./mvnw spring-boot:run
```

If running locally against a local Postgres instance, set:
- `SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres`

## API Documentation
Base paths are implemented by the current controllers and should be treated as the source of truth.

### Auth (`/api/auth`)
- **POST** `/api/auth/register`  
  Registers a new account and triggers an email verification message.
- **GET** `/api/auth/verifyemail?token=...`  
  Verifies the account using the verification token.
- **POST** `/api/auth/login`  
  Returns a JWT (as a string).
- **POST** `/api/auth/changepassword`  
  Changes the password for the authenticated user.

### Posts (`/api/post`)
- **GET** `/api/post/getAllPosts`  
  Returns all posts.
- **GET** `/api/post/getPostWithId/{id}`  
  Returns a single post by ID.
- **POST** `/api/post/addPost` *(ADMIN)*  
  Creates a post.
- **PUT** `/api/post/updatePost` *(ADMIN)*  
  Updates a post.
- **DELETE** `/api/post/deletePost/{id}` *(ADMIN)*  
  Deletes a post by ID.
- **POST** `/api/post/adminAutoAddPost` *(ADMIN)*  
  Creates a post based on Gemini-generated content.

### Comments (`/api/comment`)
- **POST** `/api/comment/addComment` *(USER)*  
  Adds a comment.
- **PUT** `/api/comment/updateComment` *(USER)*  
  Updates a comment.
- **DELETE** `/api/comment/deleteComment/{id}` *(USER)*  
  Deletes a comment by ID.

### Categories (`/api/category`)
- **POST** `/api/category/addCategory` *(ADMIN)*  
  Creates a category.
- **PUT** `/api/category/updateCategory` *(ADMIN)*  
  Updates a category.
- **DELETE** `/api/category/deleteCategory` *(ADMIN)*  
  Deletes a category (expects an `id` parameter).
- **GET** `/api/category/getCategoryWithId/{id}` *(ADMIN)*  
  Returns a category by ID.
- **GET** `/api/category/getAllCategories` *(ADMIN)*  
  Returns all categories.

### Gemini (`/api/gem`)
- **GET** `/api/gem/getContent/{category}`  
  Generates content for a given category and returns a structured response.

## Authentication & Authorization

### JWT usage
Authenticated requests must send the JWT in the `Authorization` header:

```text
Authorization: Bearer <JWT>
```

### Roles
The API uses role-based authorization with the following roles:
- `ADMIN`
- `USER`

Authorization is enforced by Spring Security rules and additional service-layer checks where applicable.

## Application Flow (step-by-step)
1. **Configure environment**
   - Copy `.env.example` → `.env`
   - Set DB credentials, `JWT_SECRET`, `GEMINI_API_KEY`, and SMTP credentials
2. **Start services**
   - `docker compose up --build`
3. **Register**
   - `POST /api/auth/register`  
   The service sends a verification link via email.
4. **Verify email**
   - Open `GET /api/auth/verifyemail?token=...`  
   The account becomes enabled.
5. **Login**
   - `POST /api/auth/login` → receive a JWT
6. **Call protected APIs**
   - Use `Authorization: Bearer <JWT>` for endpoints requiring authentication/roles
7. **Optional: AI content**
   - Generate content with `GET /api/gem/getContent/{category}`
   - As an admin, create a post with `POST /api/post/adminAutoAddPost`

## Database Notes
- **Database**: PostgreSQL
- **Default schema**: `ai_blog` (configurable via `HIBERNATE_DEFAULT_SCHEMA`)
- **DDL mode**: `update` by default (`HIBERNATE_DDL_AUTO`)
- **Docker networking**: in Compose mode, the Postgres host is `db` (not `localhost`)

### AI post creation prerequisites
The admin auto-post flow assumes the database contains:
- a `User` with email `admin@gmail.com`
- a `Category` with name `Software`

If these do not exist, the service will fail with a not-found style error.

## Important Notes / Known Issues
- **Registration assigns ADMIN role**
  - The current registration implementation sets the new user's role to `ADMIN` in `AuthServiceImpl.register`. If you expect regular users to be `USER`, update that logic accordingly.
- **Secrets management**
  - `.env` must never be committed. Use `.env.example` as a template and keep real credentials local.


