# event-ledger
Take-home project: Event Ledger microservices (Gateway + Account Service)

# Event Gateway Service (Phase 1)

This microservice is part of the **Event Ledger** project.  
It acts as the entry point for client requests, routing them to the appropriate backend services.

## Features
- Exposes REST APIs for event handling
- Acts as a gateway between clients and microservices
- Provides a foundation for future service integration
- Built with Spring Boot for simplicity and scalability

## Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Web
- Maven

## API Endpoints
| Method | Endpoint        | Description                  |
|--------|-----------------|------------------------------|
| GET    | `/events`       | Fetch all events             |
| POST   | `/events`       | Create a new event           |
| GET    | `/events/{id}`  | Fetch event details by ID    |

## Run Locally
1. Navigate to the folder:
   ```bash
   cd event-gateway
   mvn spring-boot:run
   http://localhost:8080/events
