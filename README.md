# AIBlog REST API

## Overview
AIBlog is a Spring Boot REST API for a blog platform. It provides JWT-based authentication and role-based authorization, email verification via SMTP, CRUD operations for posts, categories, and comments, and integration with the Gemini API for AI-assisted content generation. 

The application persists data using PostgreSQL with Spring Data JPA, uses MapStruct for DTO mapping, and centralizes error handling via a global exception handler. The project is containerized using Docker Compose to ensure consistent local development environments.

---

## Features

### Authentication & Security
- User registration and login
- JWT-based stateless authentication
- Role-based authorization (USER / ADMIN)
- Email verification via SMTP

### Blog Management
- Post CRUD operations
- Category management (admin-only)
- Comment CRUD operations

### AI Integration
- Gemini API integration for content generation
- Admin endpoint for AI-generated post creation

### Persistence & Architecture
- PostgreSQL database
- Spring Data JPA repositories
- Layered architecture (Controller / Service / Repository)
- DTO mapping using MapStruct
- Global exception handling

### Developer Experience
- Docker Compose support
- Externalized configuration using `.env`
- Clean modular project structure

---

## Architecture

The project follows a standard layered architecture:

- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic and rules
- **Repository Layer**: Handles database operations via JPA
- **Domain Layer**: JPA entities representing core models
- **Cross-cutting concerns**:
  - JWT Security Filter
  - Global Exception Handler
  - MapStruct Mappers
  - Gemini API client integration

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security (JWT)
- PostgreSQL
- Spring Data JPA
- MapStruct
- Maven
- Docker / Docker Compose
- Gemini API (Google Generative AI)

---

## Project Structure


src/main/java/com/Blog/AIBlog/
├── Controller
│ └── Impl
├── Service
│ └── Impl
├── Repository
├── Entity
├── Dto
│ ├── request
│ └── response
├── config
├── filter
├── util
└── exception


Root files:
- Dockerfile
- docker-compose.yml
- .env.example
- application.properties
- README.md

---

## Configuration

All sensitive configuration is managed via environment variables.

### Setup
```bash
cp .env.example .env

Do not commit .env to version control.

Environment Variables
Database
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
Hibernate
HIBERNATE_DEFAULT_SCHEMA (default: ai_blog)
HIBERNATE_DDL_AUTO (default: update)
HIBERNATE_TIME_ZONE (default: Europe/Istanbul)
JWT
JWT_SECRET (Base64 encoded)
JWT_EXP (default: 86400000)
Gemini
GEMINI_API_KEY
Mail (SMTP)
SPRING_MAIL_HOST
SPRING_MAIL_PORT
SPRING_MAIL_USERNAME
SPRING_MAIL_PASSWORD
Application
APP_BASE_URL (default: http://localhost:8080/api/auth
)
Installation & Running
Docker (Recommended)
Prerequisites
Docker Desktop
Run application
docker compose up --build
Stop application
docker compose down

API will be available at:

http://localhost:8080
Local Setup
Prerequisites
Java 21
PostgreSQL
Run
./mvnw spring-boot:run

If running locally:

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres
API Documentation
Authentication (/api/auth)
POST /register
POST /login
GET /verifyemail?token=...
POST /changepassword
Posts (/api/post)
GET /getAllPosts
GET /getPostWithId/{id}
POST /addPost (ADMIN)
PUT /updatePost (ADMIN)
DELETE /deletePost/{id} (ADMIN)
POST /adminAutoAddPost (ADMIN, AI-generated)
Comments (/api/comment)
POST /addComment (USER)
PUT /updateComment (USER)
DELETE /deleteComment/{id} (USER)
Categories (/api/category)
POST /addCategory (ADMIN)
PUT /updateCategory (ADMIN)
DELETE /deleteCategory (ADMIN)
GET /getCategoryWithId/{id} (ADMIN)
GET /getAllCategories (ADMIN)
Gemini (/api/gem)
GET /getContent/{category}

Returns AI-generated blog content for the specified category.

Authentication

All secured endpoints require JWT token:

Authorization: Bearer <JWT>

JWT contains:

user email (subject)
role (USER / ADMIN)
Application Flow
Register user account
Verify email via verification link
Login to receive JWT token
Use JWT in API requests
Access endpoints based on role permissions
Admins can generate AI-powered content
Database Notes
PostgreSQL is used as the primary database
Default schema: ai_blog
Hibernate DDL mode: update
In Docker environment, database host is db
Important Notes
Default registration assigns ADMIN role (development purpose)
.env file must not be committed
Gemini API key is required for AI features
Docker Compose must be used for consistent environment setup
