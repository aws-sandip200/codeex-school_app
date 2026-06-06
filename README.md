# SkoolBus Suite

A full-stack school bus tracking concept inspired by real parent notification workflows: delayed departures, stop arrival alerts, NFC smart-card boarding events, route visibility, incident escalation, and alternate bus assignment.

## Apps

- `frontend/` - Angular standalone dashboard for parents, fleet coordinators, and school operators.
- `backend/` - Spring Boot REST API with PostgreSQL/Supabase configuration.
- `supabase/` - SQL schema and seed data for Supabase Postgres.

## Quick start

### Frontend

```bash
cd frontend
npm install
npm start
```

The Angular app proxies API calls to `http://localhost:8080`.

### Backend

```bash
cd backend
cp .env.example .env
mvn spring-boot:run
```

### Supabase

1. Create a Supabase project.
2. Run `supabase/schema.sql` in the Supabase SQL editor.
3. Copy the project host, database password, anon key, and service role key into `backend/.env` and `frontend/src/environments/environment.ts`.

> Never commit real Supabase service keys. The included values are placeholders.
