# AIBlog API


## Overview
AIBlog is a production-oriented Spring Boot REST API designed for a modern blog platform.  
It provides a structured backend architecture with secure authentication, role-based authorization, and AI-assisted content generation.

The project emphasizes maintainability, modular design, and real-world backend development practices.

---

## Features
- JWT-based authentication and authorization  
- Email verification via SMTP  
- CRUD operations for posts, categories, and comments  
- AI-based content generation using Gemini  
- PostgreSQL integration with Spring Data JPA  
- DTO-based architecture with global exception handling  
- Dockerized environment for consistent deployment  

---

## Purpose
This project is intended to:
- Serve as a backend for a blog or CMS system  
- Demonstrate a real-world Spring Boot architecture  
- Provide practical experience with:
  - Spring Security (JWT)
  - Layered architecture
  - DTO mapping (MapStruct)
  - Exception handling
- Illustrate integration of AI services into backend systems  

---

## Roles and Authorization

| Role  | Description |
|------|------------|
| ADMIN | Full access to posts and categories, including AI-generated content |
| USER  | Can create and manage comments |

Note: Newly registered users are assigned the ADMIN role by default.  
Accounts remain inactive until email verification is completed.

---

## Application Flow
1. User registers with email and password  
2. Verification email is sent  
3. User activates account via `/api/auth/verifyemail?token=...`  
4. User logs in and receives a JWT token  
5. Token is used in requests via the Authorization header  
6. Access is granted based on role permissions  

---

## Tech Stack
- Java 21  
- Spring Boot  
- Spring Security (JWT)  
- PostgreSQL  
- Maven  
- MapStruct  
- Docker and Docker Compose  

---

## Project Structure
```
src/main/java/com/Blog/AIBlog/
├── controller
├── service
├── repository
├── entity
├── dto
├── config
├── filter
├── util
└── exception
```

---

## Configuration
Sensitive information is not stored in the repository.  
Configuration is provided through environment variables.

### Setup
```
cp .env.example .env
```

---

## Environment Variables

### Database
- SPRING_DATASOURCE_URL  
- SPRING_DATASOURCE_USERNAME  
- SPRING_DATASOURCE_PASSWORD  

### JWT
- JWT_SECRET (Base64 encoded)  
- JWT_EXP  

### Gemini
- GEMINI_API_KEY  

### Mail
- SPRING_MAIL_USERNAME  
- SPRING_MAIL_PASSWORD  

---

## Running with Docker

### Start
```
docker compose up --build
```

### Stop
```
docker compose down
```

Application runs at:
```
http://localhost:8080
```

---

## Running Locally

### Requirements
- JDK 21  
- PostgreSQL  

### Run
```
./mvnw spring-boot:run
```

---

## API Overview

### Authentication (/api/auth)
- POST /login  
- POST /register  
- GET /verifyemail  
- POST /changepassword  

### Posts (/api/post)
- POST /add  
- PUT /update  
- DELETE /delete/{id}  
- GET /all  
- GET /{id}  
- POST /generate  

### Comments (/api/comment)
- POST /add  
- PUT /update  
- DELETE /delete/{id}  

### Categories (/api/category)
- POST /add  
- PUT /update  
- DELETE /delete/{id}  
- GET /{id}  
- GET /all  
