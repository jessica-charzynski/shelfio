# 📚 Shelfio

Shelfio is a digital bookshelf application developed as a final project for the “Patterns and Frameworks” course during the Winter Semester 2025 at Berliner Hochschule für Technik (BHT).

The project is a full-stack application consisting of a React-based frontend and a Spring Boot backend.  

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

---

## API Overview

Examples of available endpoints:

- `GET /api/books` - Get all books
- `GET /api/books/latest` - Get latest book
- `GET /api/books/recent` - Get recent books
- `GET /api/books/count` - Get total book count
- `POST /api/books` - Create a new book
- `PUT /api/books/{id}/pages-read` - Update pages read
- `PUT /api/books/{id}/status` - Update book status
- `POST /api/books/{id}/review` - Add/update review
- `DELETE /api/books/{id}` - Delete a book
- `GET /api/collections` - Get all collections
- `POST /api/collections` - Create a collection
- `DELETE /api/collections/{id}` - Delete a collection
- `POST /api/collections/{id}/books/{bookId}` - Add book to collection
- `DELETE /api/collections/{id}/books/{bookId}` - Remove book from collection

All API responses follow a consistent JSON structure.