# AIBlog

## What is this project?
**AIBlog** is a **Spring Boot REST API** for a simple blog platform. It provides:
- **User registration + email verification** (SMTP)
- **JWT login** and role-based authorization
- **CRUD APIs** for **posts**, **categories**, and **comments**
- A **Gemini integration** to generate blog content
- **PostgreSQL** persistence via Spring Data JPA

## Why does it exist? (purpose)
This project is a backend API you can use to:
- build a blog admin panel or frontend on top of it
- practice a real-world Spring stack (JWT, Spring Security, JPA, DTO mapping, global exception handling)
- add AI-assisted content generation to a classic CRUD application

## Roles and access model
The app uses two roles:
- **ADMIN**: manages posts and categories (and can auto-create posts from Gemini)
- **USER**: can create/update/delete comments (read access is more open)

Important:
- The current registration implementation assigns **`ADMIN` role** on register (see `AuthServiceImpl.register`).
- A newly registered account is **disabled** until email verification is completed.

## Typical user flow (end-to-end)
1. **Register** with email + password → server sends a verification email containing a link.
2. **Verify email** by opening `/api/auth/verifyemail?token=...` → account becomes enabled.
3. **Login** → receive a JWT token (string).
4. Call protected endpoints with header:

```text
Authorization: Bearer <JWT>
```

5. Use CRUD endpoints (posts/categories/comments) based on your role.
6. Optionally call the Gemini endpoint to generate content.

## Tech stack
- **Java**: 21
- **Spring Boot**: 4.x
- **Database**: PostgreSQL
- **Build**: Maven
- **Auth**: JWT (Bearer token)
- **Mapping**: MapStruct
- **Containerization**: Docker + Docker Compose

## Project structure
Key packages under `src/main/java/com/Blog/AIBlog/`:
- **Controller**: REST endpoints (`Controller/Impl/*ControllerImpl.java`)
- **Service**: business logic (`Service/Impl/*ServiceImpl.java`)
- **Repository**: Spring Data JPA (`Repository/*Repository.java`)
- **Entity**: JPA entities (`Entity/*.java`)
- **Dto**: request/response objects (`Dto/request/*`, `Dto/response/*`)
- **config**: security + Gemini client (`config/SecurityConfig.java`, `config/GeminiClient.java`)
- **filter**: JWT filter (`filter/JwtFilter.java`)
- **util**: JWT helpers (`util/JwtUtil.java`)
- **exception**: centralized error handling (`exception/GlobalExceptionHandler.java`)

## Configuration (how this repo is meant to be used)
This repository is set up so that **secrets are not committed**.

- `src/main/resources/application.properties` contains placeholders like `${JWT_SECRET}`.
- At runtime, values are provided via environment variables.
- With Docker Compose, those values come from a local `.env` file.

### Required files (local)
Create a `.env` file in the project root.

On Windows (PowerShell):

```powershell
copy .env.example .env
```

Then open `.env` and replace all `CHANGE_ME` values.

### Required environment variables
Used by `application.properties`:

- **Database**
  - `SPRING_DATASOURCE_URL` (Docker Compose uses `jdbc:postgresql://db:5432/postgres`)
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
- **JWT**
  - `JWT_SECRET` (**must be Base64**; the app decodes it to build the signing key)
  - `JWT_EXP` (optional, default: `86400000` ms)
- **Gemini**
  - `GEMINI_API_KEY`
- **Mail (SMTP)**
  - `SPRING_MAIL_USERNAME`
  - `SPRING_MAIL_PASSWORD`
  - `SPRING_MAIL_HOST` (optional, default: `smtp.gmail.com`)
  - `SPRING_MAIL_PORT` (optional, default: `587`)
- **App**
  - `APP_BASE_URL` (optional, default: `http://localhost:8080/api/auth`)

## Running with Docker (recommended)
Requirements:
- Docker Desktop

From the project root:

```powershell
docker compose up --build
```

The API will be available at:
- `http://localhost:8080`

To stop:

```powershell
docker compose down
```

## Running locally (without Docker)
Requirements:
- JDK 21 installed

Set required environment variables in your shell, then run:

```powershell
./mvnw spring-boot:run
```

Note:
- If you run without Docker, your `SPRING_DATASOURCE_URL` should point to your local Postgres (e.g. `jdbc:postgresql://localhost:5432/postgres`).

## API endpoints
Base paths as implemented in controller classes.

### Auth (`/api/auth`)
- **POST** `/api/auth/login`
- **POST** `/api/auth/register`
- **GET** `/api/auth/verifyemail?token=...`
- **POST** `/api/auth/changepassword`

### Posts (`/api/post`)
- **POST** `/api/post/addPost` (ADMIN)
- **PUT** `/api/post/updatePost` (ADMIN)
- **DELETE** `/api/post/deletePost/{id}` (ADMIN)
- **GET** `/api/post/getAllPosts`
- **GET** `/api/post/getPostWithId/{id}`
- **POST** `/api/post/adminAutoAddPost` (ADMIN) – creates a post from Gemini-generated content

### Comments (`/api/comment`)
- **POST** `/api/comment/addComment` (USER)
- **PUT** `/api/comment/updateComment` (USER)
- **DELETE** `/api/comment/deleteComment/{id}` (USER)

### Categories (`/api/category`)
- **POST** `/api/category/addCategory` (ADMIN)
- **PUT** `/api/category/updateCategory` (ADMIN)
- **DELETE** `/api/category/deleteCategory` (ADMIN) – expects an `id` parameter
- **GET** `/api/category/getCategoryWithId/{id}` (ADMIN)
- **GET** `/api/category/getAllCategories` (ADMIN)

### Gemini (`/api/gem`)
- **GET** `/api/gem/getContent/{category}`

## Authentication (JWT)
Send the token in the Authorization header:

```text
Authorization: Bearer <JWT>
```

The JWT contains:
- subject = user email
- claim `role` = `USER` or `ADMIN`

## Authorization rules (SecurityConfig)
High-level rules:
- `/api/auth/**` is public
- Comment **GET** is public, but **write operations** require `ROLE_USER`
- Post write operations require `ROLE_ADMIN`
- Category endpoints require `ROLE_ADMIN`

Important note:
- The security config currently permits `GET /api/posts/**` (plural), while the controller uses `/api/post/...` (singular). If you see unexpected `401/403` on post GET requests, align these paths in `config/SecurityConfig.java`.

## Database notes
- Uses PostgreSQL.
- Default schema is `ai_blog` (`HIBERNATE_DEFAULT_SCHEMA`, default is set in `application.properties`).
- DDL mode defaults to `update` (`HIBERNATE_DDL_AUTO`).

### Gemini auto-post prerequisites
`/api/post/adminAutoAddPost` (and the scheduled Gemini flow in the service) assumes the database contains:
- a `User` with email `admin@gmail.com`
- a `Category` with name `Software`

If they do not exist, the service will throw a NotFound-type error.

## Troubleshooting
### Postgres connection issues in Docker
Inside Docker, `localhost` refers to the container itself. Use:
- `jdbc:postgresql://db:5432/postgres`

### Secrets accidentally committed
Never commit `.env`. It is already listed in `.gitignore`.

## License
No license specified.
