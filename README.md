# 🏭 Production Control System

![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![React](https://img.shields.io/badge/React-18-blue)
![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36)
![Tests](https://img.shields.io/badge/Tests-Unit%20%7C%20E2E-success)
![License](https://img.shields.io/badge/License-Portfolio-lightgrey)

A full stack application for production management that allows managing products, raw materials and their relationships, and calculating which products can be manufactured based on available stock.

This project demonstrates a production-ready architecture with a fully containerized environment, automated tests, clean backend structure and modern frontend integration.

---

## 🚀 Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- H2 / MySQL
- JUnit & Mockito

### Frontend
- React
- TypeScript
- Vite
- Axios
- Nginx (production build)

### DevOps
- Docker
- Docker Compose

### Testing
- Backend unit tests (Service layer)
- Frontend tests (Vitest + Testing Library)
- End-to-end tests (Cypress)

---

## 📌 Features

- Product management
- Raw material management
- Product ↔ raw material association
- Production feasibility calculation based on stock
- Global exception handling
- Structured API responses
- Request traceability with Correlation ID
- Environment-based configuration
- Fully containerized application

---

## 🧠 Business Rules

- A product can be linked to multiple raw materials
- Each raw material has an available stock quantity
- The system calculates which products can be produced based on current stock
- Production is limited by the scarcest required raw material

---

## 🏗️ Backend Architecture

```
controller → REST endpoints  
service → business rules  
repository → data access  
entity → database mapping  
dto → request/response models  
exception → global error handling  
config → filters & environment configuration  
```

### Design Patterns & Practices

- Layered architecture
- DTO pattern
- Global exception handler
- Correlation ID filter for logging
- Unit testing with mocks

---

## 🐳 Running with Docker (Recommended)

Build and start the entire application:

```bash
docker-compose up --build
```

### 🔗 Access

Frontend: http://localhost  
Backend API: http://localhost:8080  
Swagger UI: http://localhost:8080/swagger-ui.html  

The frontend is served by Nginx and API requests to `/api` are automatically proxied to the backend container.

---

## 💻 Running in Development Mode

### Backend

```bash
mvn spring-boot:run
```

Backend:

```
http://localhost:8080
```

---

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend:

```
http://localhost:5173
```

---

## 🧪 Running Tests

### Backend

```bash
mvn test
```

### Frontend

```bash
npm run test
```

### Cypress E2E

```bash
npx cypress open
```

---

## 🔗 Main API Endpoints

### Products
- GET `/products`
- POST `/products`
- PUT `/products/{id}`
- DELETE `/products/{id}`

### Raw Materials
- GET `/raw-materials`
- POST `/raw-materials`

### Product Materials
- POST `/product-materials`

### Producible Products
- GET `/products/producible`

---

## 🌱 Environment Profiles

Supported profiles:

- dev
- prod

Configured via:

```
spring.profiles.active
```

---

## 📦 Container Structure

```
production-control-api/
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── frontend/
│   ├── Dockerfile
│   └── nginx.conf
```

---

## 📚 What This Project Demonstrates

- Clean architecture with Spring Boot
- Real business logic implementation
- Full stack integration
- Production-ready Docker environment
- Reverse proxy with Nginx
- Automated testing strategy
- REST API best practices

---

## 👨‍💻 Author

Gustavo Batista  
Java & Spring Boot Backend Developer  
Full Stack Developer  

LinkedIn: www.linkedin.com/in/gustavo-batista-11a570291  
GitHub: [https://github.com/](https://github.com/GustavoBatiista/)
