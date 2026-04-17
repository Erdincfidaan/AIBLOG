# AIBlog

## Requirements
- Docker Desktop (Windows)

## Quick start (Docker)
1. Create your local environment file:
   - Copy `.env.example` to `.env`
   - Fill in the `CHANGE_ME` values

2. Build and run:

```powershell
docker compose up --build
```

3. App will be available on:
- `http://localhost:8080`

## Notes
- Secrets (JWT, API keys, mail password) must **not** be committed. Use `.env`.
- Inside Docker, Postgres host is `db` (not `localhost`).
