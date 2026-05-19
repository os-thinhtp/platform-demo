# platform-demo

Starter project for a long-term platform architecture demo with centralized SSO and integrated business portals.

## Architecture

- **platform-ui** (Angular, port `4200`): Platform portal dashboard and app launcher.
- **platform-api** (Spring Boot, port `8081`): Authenticated user profile + launcher metadata from MySQL.
- **business-a-service** (Spring Boot MVC, port `8082`): Existing-style portal A protected by OIDC.
- **business-b-service** (Spring Boot MVC, port `8083`): Existing-style portal B protected by OIDC.
- **gateway** (Spring Cloud Gateway, port `8084`): Unified backend routing.
- **keycloak** (port `8080`): Identity provider for OIDC + centralized login.
- **mysql** (port `3306`): Platform metadata persistence.

### Request / Authentication Flow

1. User opens `http://localhost:4200`.
2. Angular platform UI starts OIDC login flow against Keycloak (`platform-ui` client).
3. After login, platform UI calls gateway endpoint `http://localhost:8084/api/platform/*` with bearer token.
4. Gateway routes to `platform-api`.
5. Platform API validates JWT via Keycloak issuer, returns authenticated user info + app launcher cards from MySQL.
6. User opens Business A/B through launcher URLs (`/business-a/` and `/business-b/` on gateway).
7. Gateway routes to Business A/B services; each service uses OIDC login (`oauth2Login`) and reuses same Keycloak SSO session.

## Project Structure

- `/platform-ui`
- `/platform-api`
- `/gateway`
- `/business-a-service`
- `/business-b-service`
- `/keycloak/realm-export.json`
- `/mysql/init.sql`
- `/docker-compose.yml`

## Demo Accounts (Keycloak realm import)

Realm: `platform-demo`

- `demo.admin` / `demo123` (platform + business A + business B)
- `demo.a` / `demo123` (platform + business A)
- `demo.b` / `demo123` (platform + business B)

## Run Locally (Docker Compose)

From repository root:

```bash
docker compose up --build
```

Then open:

- Platform UI: `http://localhost:4200`
- Keycloak admin: `http://localhost:8080` (`admin` / `admin`)
- Gateway routes:
  - `http://localhost:8084/api/platform/me`
  - `http://localhost:8084/api/platform/apps`
  - `http://localhost:8084/business-a/`
  - `http://localhost:8084/business-b/`

## Build / Test Per Module

### Angular

```bash
cd platform-ui
npm install
npm run build
```

### Spring Boot services

```bash
cd platform-api && mvn test
cd ../gateway && mvn test
cd ../business-a-service && mvn test
cd ../business-b-service && mvn test
```

## Notes for Extension

- `platform-api` currently reads launcher metadata from table `applications` and can be extended with role-based visibility and audit log writes.
- `mysql/init.sql` already prepares `applications`, `user_mappings`, and `audit_logs` tables for long-term platform evolution.
- Gateway path strategy keeps services independently deployable while offering a unified entry route map.
