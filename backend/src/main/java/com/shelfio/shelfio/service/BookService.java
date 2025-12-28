package com.shelfio.shelfio.service;

import com.shelfio.shelfio.dto.response.BookResponseDto;
import java.util.List;

public interface BookService {

    BookResponseDto addBookByIsbn(String isbn);

    void deleteBook(Long bookId);

    BookResponseDto updateReadingStatus(Long bookId, String status);

    BookResponseDto addReview(Long bookId, Integer rating, String comment);

    List<BookResponseDto> getBooksByStatus(String status);

    List<BookResponseDto> getBooksByCategory(String category);

    Integer getTotalPagesRead();

    BookResponseDto updatePagesRead(Long bookId, Integer pagesRead);

    BookResponseDto getLatestBook();

    List<BookResponseDto> getLastThreeBooks();

    List<BookResponseDto> getAllBooks();

    Long getBooksCount();

    BookResponseDto createBookManually(
            String title,
            String authorFirstName,
            String authorLastName,
            String category,
            String status,
            Integer pages,
            String isbn,
            String publisher,
            String coverUrl
    );
}