AIBlog REST API
A production-ready Spring Boot REST API for a comprehensive blog platform with AI-powered content generation, JWT-based authentication, and role-based access control.
Overview
AIBlog is a complete blog management system built with Spring Boot that combines secure JWT authentication, intelligent role-based authorization, and seamless integration with Google's Gemini API for AI-assisted content creation. The application is containerized with Docker for consistent deployment across environments.
Features
Authentication and Security

User registration and login with secure password handling
JWT-based stateless authentication
Role-based access control (USER and ADMIN roles)
Email verification via SMTP for account activation
Password change functionality

Blog Management

Full CRUD operations for blog posts
Category management (admin-exclusive)
Comment system for reader engagement
Role-based endpoint protection

AI Integration

Google Gemini API integration for intelligent content generation
Admin endpoint for AI-assisted post creation by category

Data Persistence

PostgreSQL database with robust schema management
Spring Data JPA for efficient data access
Hibernate ORM with automatic schema updates

Architecture and Code Quality

Layered architecture (Controller → Service → Repository → Entity)
DTO mapping using MapStruct for clean data transfer
Global exception handling with custom error responses
Externalized configuration management

Deployment

Docker Compose for local development
Multi-container setup with PostgreSQL database
Environment-based configuration via .env file

Tech Stack
ComponentTechnologyLanguageJava 21FrameworkSpring Boot 3.xSecuritySpring Security with JWTDatabasePostgreSQLORMSpring Data JPA and HibernateMappingMapStructBuild ToolMavenContainerizationDocker and Docker ComposeAI IntegrationGoogle Gemini API
Project Structure
src/main/java/com/Blog/AIBlog/
├── controller/
│   ├── AuthController.java
│   ├── PostController.java
│   ├── CommentController.java
│   ├── CategoryController.java
│   └── GeminiController.java
├── service/
├── repository/
├── entity/
├── dto/
│   ├── request/
│   └── response/
├── config/
├── filter/
├── util/
└── exception/

Root Configuration:
├── Dockerfile
├── docker-compose.yml
├── .env.example
├── pom.xml
└── application.properties
Getting Started
Prerequisites

Docker and Docker Compose (recommended)
Or Java 21, Maven 3.8+, and PostgreSQL 14+
Git

Quick Start with Docker

Clone the repository:

bashgit clone https://github.com/Erdincfidaan/AIBLOG.git
cd AIBLOG

Configure environment variables:

bashcp .env.example .env

Edit .env with your configuration:

envSPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/ai_blog
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password
JWT_SECRET=your_base64_encoded_secret
JWT_EXP=86400000
GEMINI_API_KEY=your_gemini_api_key
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=your_email@gmail.com
SPRING_MAIL_PASSWORD=your_app_password
APP_BASE_URL=http://localhost:8080
HIBERNATE_DEFAULT_SCHEMA=ai_blog
HIBERNATE_DDL_AUTO=update
HIBERNATE_TIME_ZONE=Europe/Istanbul

Start the application:

bashdocker compose up --build
API available at http://localhost:8080.

Stop the application:

bashdocker compose down
Local Setup

Prerequisites: Java 21, Maven 3.8+, PostgreSQL 14+
Create database:

bashcreatedb ai_blog

Update application.properties with your database credentials.
Run the application:

bash./mvnw spring-boot:run
Environment Variables
Database Configuration
VariableDescriptionSPRING_DATASOURCE_URLPostgreSQL connection stringSPRING_DATASOURCE_USERNAMEDatabase usernameSPRING_DATASOURCE_PASSWORDDatabase password
Hibernate Configuration
VariableDescriptionDefaultHIBERNATE_DEFAULT_SCHEMADefault schema nameai_blogHIBERNATE_DDL_AUTOSchema update strategyupdateHIBERNATE_TIME_ZONEApplication timezoneEurope/Istanbul
JWT Configuration
VariableDescriptionJWT_SECRETBase64-encoded secret keyJWT_EXPToken expiration time in milliseconds
External Services
VariableDescriptionGEMINI_API_KEYGoogle Gemini API keySPRING_MAIL_HOSTSMTP server hostSPRING_MAIL_PORTSMTP server portSPRING_MAIL_USERNAMEEmail accountSPRING_MAIL_PASSWORDEmail passwordAPP_BASE_URLBase application URL
API Endpoints
Authentication (/api/auth)
MethodEndpointDescriptionAuthenticationPOST/registerCreate new user accountNoPOST/loginAuthenticate and receive JWTNoGET/verifyemailVerify email with tokenNoPOST/changepasswordChange passwordYes
Posts (/api/post)
MethodEndpointDescriptionRoleGET/getAllPostsRetrieve all published postsPublicGET/getPostWithId/{id}Get specific post by IDPublicPOST/addPostCreate new blog postADMINPUT/updatePostUpdate existing postADMINDELETE/deletePost/{id}Delete post by IDADMINPOST/adminAutoAddPostAI-generated post creationADMIN
Comments (/api/comment)
MethodEndpointDescriptionRolePOST/addCommentAdd comment to postUSERPUT/updateCommentEdit your commentUSERDELETE/deleteComment/{id}Delete commentUSER
Categories (/api/category)
MethodEndpointDescriptionRolePOST/addCategoryCreate categoryADMINPUT/updateCategoryUpdate categoryADMINDELETE/deleteCategory/{id}Delete categoryADMINGET/getCategoryWithId/{id}Get category detailsADMINGET/getAllCategoriesList all categoriesADMIN
Gemini AI (/api/gem)
MethodEndpointDescriptionGET/getContent/{category}Generate AI content for category
Authentication
All protected endpoints require the Authorization header with a Bearer token:
Authorization: Bearer <JWT_TOKEN>
Token Structure
The JWT token contains:
json{
  "sub": "user@example.com",
  "role": "ADMIN",
  "iat": 1234567890,
  "exp": 1234571490
}
Roles and Permissions
USER Role

Read all public content
Create and manage comments
Change password

ADMIN Role

All USER permissions
Create, update, delete posts
Manage categories
Generate AI content

Database Schema
users

id (Primary Key)
email (Unique)
password (encrypted)
full_name
role (USER/ADMIN)
email_verified (boolean)
created_at

posts

id (Primary Key)
title
content
category_id (Foreign Key)
author_id (Foreign Key → users)
created_at
updated_at

categories

id (Primary Key)
name
description
created_at

comments

id (Primary Key)
content
post_id (Foreign Key)
author_id (Foreign Key → users)
created_at
updated_at

Security Best Practices

Passwords are hashed using Spring Security
JWT tokens with configurable expiration
Email verification for new accounts
Role-based access control
Global exception handling without stack trace exposure
Input validation on all endpoints
HTTPS required for production

Configuration
Application Properties
propertiesserver.port=8080
server.servlet.context-path=/
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jackson.serialization.indent-output=true
server.error.include-message=always
server.error.include-binding-errors=always
Troubleshooting
Connection Refused
If you encounter a "Connection refused" error when connecting to PostgreSQL:

Ensure Docker container is running: docker compose ps
Verify PostgreSQL is installed and running locally
Check database credentials in .env file

JWT Token Expired
If you receive a 401 Unauthorized error:

Login again to get a new token
Increase JWT_EXP value if needed
Default expiration is 24 hours

Email Verification Failed
SMTP connection issues:

Verify SMTP credentials in .env file
Enable "Less secure app access" if using Gmail
Check firewall and port access

Gemini API Error
Invalid API key or connection errors:

Verify GEMINI_API_KEY in .env file
Check API key validity at Google Cloud Console
Ensure API is enabled in your project

Important Notes

Default registration assigns ADMIN role (change in production)
Never commit .env file to version control
Always use HTTPS in production environments
Implement rate limiting for production deployments
Set up proper logging and monitoring
