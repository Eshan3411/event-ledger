# Account Service (Phase 2)

This microservice is part of the **Event Ledger** project.  
It manages account information, balances, and provides REST APIs for CRUD operations.

## Features
- Create new accounts with owner name and balance
- Retrieve all accounts
- Fetch account details by ID
- In‑memory H2 database for quick demo
- RESTful endpoints built with Spring Boot

## Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Lombok

## API Endpoints
| Method | Endpoint        | Description              |
|--------|-----------------|--------------------------|
| GET    | `/accounts`     | Fetch all accounts       |
| POST   | `/accounts`     | Create a new account     |
| GET    | `/accounts/{id}`| Fetch account by ID      |

## Run Locally
1. Navigate to the folder:
   ```bash
   cd account-service
   mvn spring-boot:run
   http://localhost:8080/accounts

