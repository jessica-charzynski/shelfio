package com.shelfio.shelfio.repository;

import com.shelfio.shelfio.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {


    Optional<Book> findByIsbn(String isbn);


    List<Book> findByReadingStatus_Status(String status);


    List<Book> findByCategory_Name(String name);

    List<Book> findByReadingStatus_StatusIgnoreCase(String status);

    List<Book> findByCategory_NameIgnoreCase(String categoryName);

    @Query("SELECT COALESCE(SUM(b.pagesRead), 0) FROM Book b")
    Integer getTotalPagesRead();

    // Get latest book (most recently added)
    Optional<Book> findFirstByOrderByBookIdDesc();

    // Get last N books ordered by ID descending
    List<Book> findTop3ByOrderByBookIdDesc();

    // Get all books ordered by most recent first
    List<Book> findAllByOrderByBookIdDesc();
}
