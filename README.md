# ShopLite

A deliberately simple microservices shop, built one concept at a time to learn backend engineering at production standard.

**Stack:** Java 21 · Spring Boot 3 · PostgreSQL 16 · Docker · Kafka · Redis · React

## Run locally

**Prerequisites:** Docker Desktop, Java 21, Maven 3.9+

```bash
cp .env.example .env        # then replace every change_me with a real value
docker compose up -d
docker compose ps           # postgres should show (healthy)
```

## Architecture (so far)

- One Postgres 16 server (Docker), host port 5433
- One database per service: `productdb`, `orderdb`, `authdb`
- Each database owned by its own user; cross-database access revoked (least privilege)

## Progress

| Step | What I learned |
|---|---|
| 01 | Monorepo layout, database-per-service with least-privilege users, config via environment variables, Docker volumes and init scripts |
| 02 | product-service CRUD built three ways behind one interface: raw JDBC (PreparedStatement, ResultSet mapping, SQL injection demo, connection-leak demo), JdbcTemplate (template method, exception translation), Spring Data JPA (entity mapping, derived queries, ddl-auto=validate with Flyway) |