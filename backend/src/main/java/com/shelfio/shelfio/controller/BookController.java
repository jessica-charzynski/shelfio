package com.shelfio.shelfio.controller;

import com.shelfio.shelfio.dto.request.AddReviewRequest;
import com.shelfio.shelfio.dto.request.CreateBookRequest;
import com.shelfio.shelfio.dto.request.UpdatePagesReadRequest;
import com.shelfio.shelfio.dto.request.UpdateReadingStatusRequest;
import com.shelfio.shelfio.dto.response.ApiResponse;
import com.shelfio.shelfio.dto.response.BookResponseDto;
import com.shelfio.shelfio.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/isbn/{isbn}")
    public ResponseEntity<ApiResponse<BookResponseDto>> addBookByIsbn(
            @PathVariable @NotBlank(message = "ISBN is required") String isbn) {

        log.info("Request to add book with ISBN: {}", isbn);
        BookResponseDto book = bookService.addBookByIsbn(isbn);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book added successfully", book));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long bookId) {
        log.info("Request to delete book with ID: {}", bookId);
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponseDto>> createBookManually(
            @Valid @RequestBody CreateBookRequest request) {

        log.info("Request to manually add book: {}", request.getTitle());

        BookResponseDto book = bookService.createBookManually(
                request.getTitle(),
                request.getAuthorFirstName(),
                request.getAuthorLastName(),
                request.getCategory(),
                request.getStatus(),
                request.getPages(),
                request.getIsbn(),
                request.getPublisher(),
                request.getCoverUrl()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book added to library successfully", book));
    }

    @PutMapping("/{bookId}/status")
    public ResponseEntity<ApiResponse<BookResponseDto>> updateReadingStatus(
            @PathVariable Long bookId,
            @Valid @RequestBody UpdateReadingStatusRequest request) {

        log.info("Request to update reading status for book ID: {} to status: {}",
                bookId, request.getStatus());

        BookResponseDto book = bookService.updateReadingStatus(bookId, request.getStatus());
        return ResponseEntity.ok(ApiResponse.success("Reading status updated successfully", book));
    }

    @PutMapping("/{bookId}/pages-read")
    public ResponseEntity<ApiResponse<BookResponseDto>> updatePagesRead(
            @PathVariable Long bookId,
            @Valid @RequestBody UpdatePagesReadRequest request) {

        log.info("Request to update pages read for book ID: {} to {} pages",
                bookId, request.getPagesRead());

        BookResponseDto book = bookService.updatePagesRead(bookId, request.getPagesRead());
        return ResponseEntity.ok(ApiResponse.success("Pages read updated successfully", book));
    }

    @PostMapping("/{bookId}/review")
    public ResponseEntity<ApiResponse<BookResponseDto>> addReview(
            @PathVariable Long bookId,
            @Valid @RequestBody AddReviewRequest request) {

        log.info("Request to add review for book ID: {} with rating: {}",
                bookId, request.getRating());

        BookResponseDto book = bookService.addReview(
                bookId,
                request.getRating(),
                request.getComment());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Review added successfully", book));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> getBooksByStatus(
            @PathVariable @NotBlank(message = "Status is required") String status) {

        log.info("Request to get books by status: {}", status);
        List<BookResponseDto> books = bookService.getBooksByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> getBooksByCategory(
            @PathVariable @NotBlank(message = "Category is required") String category) {

        log.info("Request to get books by category: {}", category);
        List<BookResponseDto> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @GetMapping("/stats/pages-read")
    public ResponseEntity<ApiResponse<Integer>> getTotalPagesRead() {
        log.info("Request to get total pages read");
        Integer totalPages = bookService.getTotalPagesRead();
        return ResponseEntity.ok(ApiResponse.success("Total pages calculated successfully", totalPages));
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<BookResponseDto>> getLatestBook() {
        log.info("Request to get latest book");
        BookResponseDto book = bookService.getLatestBook();
        return ResponseEntity.ok(ApiResponse.success(book));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> getLastThreeBooks() {
        log.info("Request to get last 3 books");
        List<BookResponseDto> books = bookService.getLastThreeBooks();
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponseDto>>> getAllBooks() {
        log.info("Request to get all books");
        List<BookResponseDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(ApiResponse.success(books));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getBooksCount() {
        log.info("Request to get books count");
        Long count = bookService.getBooksCount();
        return ResponseEntity.ok(ApiResponse.success("Total books retrieved successfully", count));
    }
}