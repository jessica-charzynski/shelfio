package com.shelfio.shelfio.service.impl;

import com.shelfio.shelfio.adapter.BookDataAdapter;
import com.shelfio.shelfio.dto.response.BookResponseDto;
import com.shelfio.shelfio.dto.ExternalBookDto;
import com.shelfio.shelfio.exception.ExternalServiceException;
import com.shelfio.shelfio.exception.InvalidInputException;
import com.shelfio.shelfio.exception.ResourceAlreadyExistsException;
import com.shelfio.shelfio.exception.ResourceNotFoundException;
import com.shelfio.shelfio.mapper.BookMapper;
import com.shelfio.shelfio.entity.*;
import com.shelfio.shelfio.repository.*;
import com.shelfio.shelfio.service.BookService;
import com.shelfio.shelfio.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ReadingStatusRepository readingStatusRepository;
    private final ReviewRepository reviewRepository;
    private final BookDataAdapter bookDataAdapter;
    private final ReviewService reviewService;

    @Override
    public BookResponseDto addBookByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new InvalidInputException("ISBN cannot be null or empty");
        }

        return bookRepository.findByIsbn(isbn)
                .map(book -> {
                    log.info("Book with ISBN {} already exists in database", isbn);
                    return BookMapper.toDto(book);
                })
                .orElseGet(() -> {
                    log.info("Fetching book data from external service for ISBN: {}", isbn);

                    ExternalBookDto external = bookDataAdapter
                            .fetchBookByIsbn(isbn)
                            .orElseThrow(() -> new ExternalServiceException(
                                    "No book found for ISBN: " + isbn));

                    Author author = findOrCreateAuthor(
                            external.getAuthorFirstName(),
                            external.getAuthorLastName());

                    Category category = findOrCreateCategory(external.getCategories());

                    ReadingStatus notStarted = readingStatusRepository
                            .findByStatus("Not started")
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Reading status 'Not started' not found in database"));

                    Book book = Book.builder()
                            .title(external.getTitle())
                            .isbn(external.getIsbn())
                            .pages(external.getPages())
                            .pagesRead(0)
                            .publisher(external.getPublisher())
                            .bookcover(external.getCoverUrl())
                            .author(author)
                            .category(category)
                            .readingStatus(notStarted)
                            .build();

                    Book savedBook = bookRepository.save(book);
                    log.info("Successfully saved book with ID: {}", savedBook.getBookId());

                    return BookMapper.toDto(savedBook);
                });
    }

    @Override
    public void deleteBook(Long bookId) {
        if (bookId == null) {
            throw new InvalidInputException("Book ID cannot be null");
        }

        // Check if book exists
        if (!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found with ID: " + bookId);
        }

        // Delete the book
        bookRepository.deleteById(bookId);

        log.info("Deleted book with ID: {}", bookId);
    }

    @Override
    public BookResponseDto createBookManually(
            String title,
            String authorFirstName,
            String authorLastName,
            String category,
            String status,
            Integer pages,
            String isbn,
            String publisher,
            String coverUrl) {

        // Validate required fields
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidInputException("Title is required");
        }
        if (authorFirstName == null || authorFirstName.trim().isEmpty()) {
            throw new InvalidInputException("Author first name is required");
        }
        if (authorLastName == null || authorLastName.trim().isEmpty()) {
            throw new InvalidInputException("Author last name is required");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new InvalidInputException("Category is required");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new InvalidInputException("Status is required");
        }

        // Check if ISBN already exists (if provided)
        if (isbn != null && !isbn.trim().isEmpty()) {
            Optional<Book> existingBook = bookRepository.findByIsbn(isbn);
            if (existingBook.isPresent()) {
                throw new ResourceAlreadyExistsException(
                        "Book with ISBN " + isbn + " already exists");
            }
        }

        // Find or create author
        Author author = authorRepository
                .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
                        authorFirstName.trim(),
                        authorLastName.trim())
                .orElseGet(() -> {
                    log.info("Creating new author: {} {}", authorFirstName, authorLastName);
                    return authorRepository.save(
                            Author.builder()
                                    .firstName(authorFirstName.trim())
                                    .lastName(authorLastName.trim())
                                    .build());
                });

        // Find or create category
        Category bookCategory = categoryRepository
                .findByNameIgnoreCase(category.trim())
                .orElseGet(() -> {
                    log.info("Creating new category: {}", category);
                    return categoryRepository.save(
                            Category.builder()
                                    .name(category.trim())
                                    .build());
                });

        // Find reading status
        ReadingStatus readingStatus = readingStatusRepository
                .findByStatus(status.trim())
                .orElseThrow(() -> new InvalidInputException(
                        "Invalid reading status: " + status +
                                ". Must be one of: Not started, Reading, Finished"));

        // Create book
        Book book = Book.builder()
                .title(title.trim())
                .author(author)
                .category(bookCategory)
                .readingStatus(readingStatus)
                .pages(pages)
                .pagesRead(0)
                .isbn(isbn != null ? isbn.trim() : null)
                .publisher(publisher != null ? publisher.trim() : null)
                .bookcover(coverUrl != null ? coverUrl.trim() : null)
                .build();

        Book savedBook = bookRepository.save(book);
        log.info("Manually created book with ID: {} - {}", savedBook.getBookId(), savedBook.getTitle());

        return BookMapper.toDto(savedBook);
    }

    private Author findOrCreateAuthor(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new InvalidInputException("Author first name and last name cannot be null");
        }

        return authorRepository
                .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName)
                .orElseGet(() -> {
                    log.info("Creating new author: {} {}", firstName, lastName);
                    return authorRepository.save(
                            Author.builder()
                                    .firstName(firstName)
                                    .lastName(lastName)
                                    .build());
                });
    }

    private Category findOrCreateCategory(List<String> categories) {
        String categoryName = (categories != null && !categories.isEmpty())
                ? categories.get(0)
                : "Uncategorized";

        return categoryRepository
                .findByNameIgnoreCase(categoryName)
                .orElseGet(() -> {
                    log.info("Creating new category: {}", categoryName);
                    return categoryRepository.save(
                            Category.builder()
                                    .name(categoryName)
                                    .build());
                });
    }

    @Override
    public BookResponseDto updateReadingStatus(Long bookId, String status) {
        if (bookId == null) {
            throw new InvalidInputException("Book ID cannot be null");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new InvalidInputException("Status cannot be null or empty");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with ID: " + bookId));

        ReadingStatus readingStatus = readingStatusRepository
                .findByStatus(status)
                .orElseThrow(() -> new InvalidInputException(
                        "Invalid reading status: " + status));

        book.setReadingStatus(readingStatus);
        Book updatedBook = bookRepository.save(book);

        log.info("Updated reading status for book ID: {} to {}", bookId, status);
        return BookMapper.toDto(updatedBook);
    }

    @Override
    public BookResponseDto addReview(Long bookId, Integer rating, String comment) {
        // Delegate to review service
        reviewService.addReview(bookId, rating, comment);

        // Return updated book with reviews
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with ID: " + bookId));

        return BookMapper.toDto(book);
    }

    @Override
    public List<BookResponseDto> getBooksByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new InvalidInputException("Status cannot be null or empty");
        }

        List<Book> books = bookRepository.findByReadingStatus_StatusIgnoreCase(status);
        log.info("Found {} books with status: {}", books.size(), status);

        return books.stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookResponseDto> getBooksByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new InvalidInputException("Category cannot be null or empty");
        }

        List<Book> books = bookRepository.findByCategory_NameIgnoreCase(category);
        log.info("Found {} books in category: {}", books.size(), category);

        return books.stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Override
    public Integer getTotalPagesRead() {
        Integer totalPages = bookRepository.getTotalPagesRead();
        log.info("Total pages read: {}", totalPages != null ? totalPages : 0);
        return totalPages != null ? totalPages : 0;
    }

    @Override
    public BookResponseDto updatePagesRead(Long bookId, Integer pagesRead) {
        if (bookId == null) {
            throw new InvalidInputException("Book ID cannot be null");
        }
        if (pagesRead == null || pagesRead < 0) {
            throw new InvalidInputException("Pages read cannot be null or negative");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with ID: " + bookId));

        // Optional: Validate pagesRead doesn't exceed total pages
        if (book.getPages() != null && pagesRead > book.getPages()) {
            throw new InvalidInputException(
                    "Pages read (" + pagesRead + ") cannot exceed total pages (" + book.getPages() + ")");
        }

        book.setPagesRead(pagesRead);
        Book updatedBook = bookRepository.save(book);

        log.info("Updated pages read for book ID: {} to {} pages", bookId, pagesRead);
        return BookMapper.toDto(updatedBook);
    }

    @Override
    public BookResponseDto getLatestBook() {
        log.info("Fetching latest book");

        Book book = bookRepository.findFirstByOrderByBookIdDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No books found in library"));

        return BookMapper.toDto(book);
    }

    @Override
    public List<BookResponseDto> getLastThreeBooks() {
        log.info("Fetching last 3 books");

        List<Book> books = bookRepository.findTop3ByOrderByBookIdDesc();

        if (books.isEmpty()) {
            log.info("No books found in library");
        } else {
            log.info("Found {} books", books.size());
        }

        return books.stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        log.info("Fetching all books");

        List<Book> books = bookRepository.findAllByOrderByBookIdDesc();
        log.info("Found {} books", books.size());

        return books.stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Override
    public Long getBooksCount() {
        long count = bookRepository.count();
        log.info("Total books count: {}", count);
        return count;
    }
}