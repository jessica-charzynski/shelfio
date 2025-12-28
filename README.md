# 📚 Shelfio

Shelfio is a digital bookshelf application developed as a final project for the “Patterns and Frameworks” course during the Winter Semester 2025 at Berliner Hochschule für Technik (BHT).
The project is a full-stack application consisting of a React-based frontend and a Spring Boot backend.  

<img width="1435" height="683" alt="shelfio-dashboard" src="https://github.com/user-attachments/assets/a7a11d24-7b14-4344-a39e-a8ad18bd13da" />

---

## Features

- 📚 **Library Management**: Add, view, and manage your book collection  
- 📖 **Reading Progress**: Track pages read and reading status  
- ⭐ **Ratings**: Rate your books  
- 📁 **Collections**: Organize books into custom collections  
- 🔍 **Search & Filter**: Find books by title, author, category, or status  
- 📊 **Statistics**: View your reading statistics and progress  

---

## Tech Stack

### Frontend
- React 18  
- TypeScript  
- Vite  
- Tailwind CSS  
- shadcn/ui  

### Backend
- Java 17  
- Spring Boot  
- Spring Data JPA  
- Hibernate  
- PostgreSQL  

---

## Patterns

- **Data Transfer Object (DTO)**  
  DTOs define which data is exchanged between backend and frontend.  
  Database entities are not sent directly via the API.

- **Adapter Pattern**  
  The Adapter pattern converts external book data into the internal `Book` format.  

---

## Getting Started

### Prerequisites
- Node.js 
- Java 17
- PostgreSQL
- Maven

---

### Backend Setup

1. Create a PostgreSQL database:
   ```sql
   CREATE DATABASE shelfio;
   ```

2. Configure `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/shelfio
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Start the backend:
   ```bash
   mvn spring-boot:run
   ```

The backend runs on `http://localhost:8080`.

---

### Frontend Setup

1. Install dependencies:
   ```bash
   npm install
   ```

2. Create a `.env` file:
   ```env
   VITE_API_BASE_URL=http://localhost:8080
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

The frontend runs on `http://localhost:8081`.
