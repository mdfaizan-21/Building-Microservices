# 🏢 Job Application Microservices Platform

A comprehensive **Job Application Management System** built with **Spring Boot**, demonstrating the evolution from a monolithic architecture to a microservices-based architecture. The platform enables companies to post job listings, applicants to browse opportunities, and users to submit reviews for companies.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Microservices](#microservices)
- [Project Evolution](#project-evolution)
- [Entity Relationship](#entity-relationship)
- [API Reference](#api-reference)
- [Getting Started](#getting-started)
- [Docker Setup](#docker-setup)
- [Project Structure](#project-structure)
- [Git History & Branching Strategy](#git-history--branching-strategy)
- [Contributing](#contributing)

---

## 🔭 Overview

This project is a **Job Application Platform** that provides RESTful APIs for managing:

- **Jobs** — Create, read, update, and delete job postings with details like title, description, salary range, and location
- **Companies** — Manage company profiles including name, description, and associated reviews
- **Reviews** — Submit and manage company reviews with titles, descriptions, and ratings

The project showcases a real-world architectural journey from a **monolithic Spring Boot application** to a **distributed microservices architecture** with **service discovery** via Netflix Eureka.

---

## 🏗️ Architecture

### Monolithic Architecture (Initial)

The application started as a single monolith with tightly-coupled JPA entities sharing a single PostgreSQL database.

```
┌─────────────────────────────────────────────┐
│           Monolith (Port 8090)              │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐    │
│  │   Job    │ │ Company  │ │  Review  │    │
│  │ Module   │ │ Module   │ │  Module  │    │
│  └────┬─────┘ └────┬─────┘ └────┬─────┘    │
│       │             │            │          │
│       └─────────────┼────────────┘          │
│                     ▼                       │
│            PostgreSQL (jobApp)              │
└─────────────────────────────────────────────┘
```

### Microservices Architecture (Current)

The application has been decomposed into independently deployable services with their own databases and a centralized service registry.

```
                    ┌──────────────────────┐
                    │   Eureka Service     │
                    │   Registry (8761)    │
                    └──────────┬───────────┘
                               │
          ┌────────────────────┼────────────────────┐
          │                    │                     │
          ▼                    ▼                     ▼
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│   Job Service   │  │ Company Service │  │ Review Service  │
│    (8081)       │  │    (8082)       │  │    (8083)       │
│                 │  │                 │  │                 │
│  PostgreSQL:    │  │  PostgreSQL:    │  │  PostgreSQL:    │
│  job            │  │  company        │  │  review         │
└─────────────────┘  └─────────────────┘  └─────────────────┘
         │                    ▲
         │    RestTemplate    │
         └────────────────────┘
```

---

## 🛠️ Tech Stack

| Technology                      | Purpose                          |
| ------------------------------- | -------------------------------- |
| **Java 21**                     | Programming Language             |
| **Spring Boot 3.5.x**           | Application Framework            |
| **Spring Data JPA**             | Data Persistence & ORM           |
| **Spring Web**                  | RESTful API Development          |
| **Spring Boot Actuator**        | Health Monitoring & Metrics      |
| **Spring Cloud Netflix Eureka** | Service Discovery & Registration |
| **PostgreSQL**                  | Relational Database              |
| **Docker**                      | Containerization                 |
| **Docker Compose**              | Multi-Container Orchestration    |
| **Maven**                       | Build & Dependency Management    |
| **pgAdmin 4**                   | Database Administration GUI      |

---

## 🧩 Microservices

### 1. 📌 Job Microservice (`jobms`)

- **Port:** `8081`
- **Database:** `job`
- **Base Path:** `/jobs`
- **Description:** Manages job postings. Communicates with the Company Service via `RestTemplate` to enrich job listings with company details using a `JobWithCompanyDTO`.
- **Eureka Client:** ✅ Registered

### 2. 🏭 Company Microservice (`companyms`)

- **Port:** `8082`
- **Database:** `company`
- **Base Path:** `/company`
- **Description:** Manages company profiles including name, description, and review summaries. Operates as an independent service.

### 3. ⭐ Review Microservice (`reviewms`)

- **Port:** `8083`
- **Database:** `review`
- **Base Path:** `/review`
- **Description:** Manages company reviews. Reviews are linked to companies via `companyId` and support full CRUD operations.

### 4. 🗂️ Service Registry (`service`)

- **Port:** `8761`
- **Description:** Netflix Eureka Server for service discovery. All microservices register here for inter-service communication.

---

## 📈 Project Evolution

The project follows a progressive development journey, transitioning from a monolith to microservices:

| Phase       | Description                                                                                            |
| ----------- | ------------------------------------------------------------------------------------------------------ |
| **Phase 1** | Built as a monolithic Spring Boot application with Spring Boot Actuator for health monitoring          |
| **Phase 2** | Introduced the Review system, establishing `Company ↔ Job` and `Company ↔ Review` JPA relationships    |
| **Phase 3** | Decomposed into 3 independent microservices (`jobms`, `companyms`, `reviewms`) with separate databases |
| **Phase 4** | Implemented inter-service communication between Job and Company using `RestTemplate`                   |
| **Phase 5** | Added Netflix Eureka Service Registry for service discovery                                            |
| **Phase 6** | Registered Job Microservice as a Eureka client                                                         |

---

## 🔗 Entity Relationship

### Monolith (JPA Relationships)

```
┌──────────────┐       ┌──────────────┐       ┌──────────────┐
│     Job      │       │   Company    │       │    Review    │
├──────────────┤       ├──────────────┤       ├──────────────┤
│ id           │       │ id           │       │ id           │
│ title        │  M:1  │ name         │  1:M  │ title        │
│ description  │◄──────│ description  │──────►│ description  │
│ minSalary    │       │ review       │       │ rating       │
│ maxSalary    │       │ jobs (List)  │       │ company      │
│ location     │       │ reviews(List)│       └──────────────┘
│ company      │       └──────────────┘
└──────────────┘
```

### Microservices (Decoupled via companyId)

```
Job (jobms)          Company (companyms)     Review (reviewms)
├── id               ├── id                  ├── id
├── title            ├── name                ├── title
├── description      ├── description         ├── description
├── minSalary        └── review              ├── rating
├── maxSalary                                └── companyId
├── location
└── companyId
```

---

## 📡 API Reference

### Job Service — `http://localhost:8081/jobs`

| Method   | Endpoint     | Description                 |
| -------- | ------------ | --------------------------- |
| `GET`    | `/jobs`      | Get all jobs (with company) |
| `GET`    | `/jobs/{id}` | Get a specific job by ID    |
| `POST`   | `/jobs`      | Create a new job posting    |
| `PUT`    | `/jobs/{id}` | Update an existing job      |
| `DELETE` | `/jobs/{id}` | Delete a job posting        |

**Sample Job Request Body:**

```json
{
  "title": "Software Engineer",
  "description": "Build scalable microservices",
  "minSalary": "80000",
  "maxSalary": "120000",
  "location": "Remote",
  "companyId": 1
}
```

### Company Service — `http://localhost:8082/company`

| Method   | Endpoint        | Description            |
| -------- | --------------- | ---------------------- |
| `GET`    | `/company`      | Get all companies      |
| `GET`    | `/company/{id}` | Get a specific company |
| `POST`   | `/company`      | Create a new company   |
| `PUT`    | `/company/{id}` | Update a company       |
| `DELETE` | `/company/{id}` | Delete a company       |

**Sample Company Request Body:**

```json
{
  "name": "Tech Corp",
  "description": "Leading technology company",
  "review": "Great workplace"
}
```

### Review Service — `http://localhost:8083/review`

| Method   | Endpoint              | Description                   |
| -------- | --------------------- | ----------------------------- |
| `GET`    | `/review?companyId=1` | Get all reviews for a company |
| `GET`    | `/review/{reviewId}`  | Get a specific review         |
| `POST`   | `/review?companyId=1` | Add a review for a company    |
| `PUT`    | `/review/{reviewId}`  | Update a review               |
| `DELETE` | `/review/{reviewId}`  | Delete a review               |

**Sample Review Request Body:**

```json
{
  "title": "Amazing Experience",
  "description": "Great work culture and benefits",
  "rating": "4.5"
}
```

### Eureka Dashboard — `http://localhost:8761`

Access the Eureka service registry dashboard to view all registered microservices.

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** (JDK)
- **Maven 3.8+**
- **PostgreSQL** (or use Docker)
- **Docker & Docker Compose** (optional, for containerized setup)

---

## 📂 Project Structure

```
jobApplication/
├── 📄 pom.xml                          # Root project (Monolith)
├── 🐳 Dockerfile                       # Multi-stage Docker build
├── 🐳 docker-compose.yaml              # PostgreSQL + pgAdmin
│
├── 📁 src/                             # Monolith source code
│   └── main/java/com/example/jobApplication/
│       ├── JobApplication.java         # Main entry point
│       ├── job/
│       │   ├── Job.java                # Job entity (JPA)
│       │   ├── JobController.java      # REST controller
│       │   ├── JobService.java         # Service interface
│       │   ├── JobRepository.java      # JPA repository
│       │   └── impl/
│       │       └── JobServiceImpl.java # Service implementation
│       ├── company/
│       │   ├── Company.java            # Company entity (JPA)
│       │   ├── CompanyController.java
│       │   ├── CompanyService.java
│       │   ├── CompanyRepository.java
│       │   └── impl/
│       │       └── CompanyServiceImpl.java
│       └── review/
│           ├── Review.java             # Review entity (JPA)
│           ├── ReviewController.java
│           ├── ReviewService.java
│           ├── ReviewRepository.java
│           └── impl/
│               └── ReviewServiceImpl.java
│
├── 📁 jobms/                           # Job Microservice
│   ├── pom.xml
│   └── src/main/java/com/example/jobms/
│       ├── JobmsApplication.java
│       ├── dto/
│       │   └── JobWithCompanyDTO.java  # DTO for Job + Company
│       ├── external/
│       │   └── Company.java           # External Company model
│       └── job/
│           ├── Job.java               # Job entity (companyId ref)
│           ├── JobController.java
│           ├── JobService.java
│           ├── JobRepository.java
│           └── impl/
│               └── JobServiceImpl.java # Uses RestTemplate
│
├── 📁 companyms/                       # Company Microservice
│   ├── pom.xml
│   └── src/main/java/com/example/companyms/
│       ├── CompanymsApplication.java
│       └── company/
│           ├── Company.java
│           ├── CompanyController.java
│           ├── CompanyService.java
│           ├── CompanyRepository.java
│           └── impl/
│               └── CompanyServiceImpl.java
│
├── 📁 reviewms/                        # Review Microservice
│   ├── pom.xml
│   └── src/main/java/com/example/review/
│       ├── ReviewmsApplication.java
│       ├── Review.java
│       ├── ReviewController.java
│       ├── ReviewService.java
│       ├── ReviewRepository.java
│       └── impl/
│           └── ReviewServiceImpl.java
│
└── 📁 service/                         # Eureka Service Registry
    ├── pom.xml
    └── src/main/java/com/example/service/
        └── ServiceApplication.java     # @EnableEurekaServer
```

---
