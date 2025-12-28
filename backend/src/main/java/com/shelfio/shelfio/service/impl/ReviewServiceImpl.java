package com.shelfio.shelfio.service.impl;

import com.shelfio.shelfio.dto.response.ReviewResponseDto;
import com.shelfio.shelfio.entity.Book;
import com.shelfio.shelfio.entity.Review;
import com.shelfio.shelfio.exception.InvalidInputException;
import com.shelfio.shelfio.exception.ResourceNotFoundException;
import com.shelfio.shelfio.mapper.ReviewMapper;
import com.shelfio.shelfio.repository.BookRepository;
import com.shelfio.shelfio.repository.ReviewRepository;
import com.shelfio.shelfio.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Override
    public ReviewResponseDto addReview(Long bookId, Integer rating, String comment) {
        if (bookId == null) {
            throw new InvalidInputException("Book ID cannot be null");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new InvalidInputException("Rating must be between 1 and 5");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with ID: " + bookId));

        Review review = Review.builder()
                .book(book)
                .rating(rating)
                .comment(comment)
                .build();

        Review savedReview = reviewRepository.save(review);
        log.info("Added review for book ID: {} with rating: {}", bookId, rating);

        return ReviewMapper.toDto(savedReview);
    }

    @Override
    public ReviewResponseDto updateReview(Long reviewId, Integer rating, String comment) {
        if (reviewId == null) {
            throw new InvalidInputException("Review ID cannot be null");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new InvalidInputException("Rating must be between 1 and 5");
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review not found with ID: " + reviewId));

        review.setRating(rating);
        review.setComment(comment);

        Review updatedReview = reviewRepository.save(review);
        log.info("Updated review ID: {} - New rating: {}", reviewId, rating);

        return ReviewMapper.toDto(updatedReview);
    }

    @Override
    public void deleteReview(Long reviewId) {
        if (reviewId == null) {
            throw new InvalidInputException("Review ID cannot be null");
        }

        if (!reviewRepository.existsById(reviewId)) {
            throw new ResourceNotFoundException("Review not found with ID: " + reviewId);
        }

        reviewRepository.deleteById(reviewId);
        log.info("Deleted review with ID: {}", reviewId);
    }

    @Override
    public ReviewResponseDto getReviewById(Long reviewId) {
        if (reviewId == null) {
            throw new InvalidInputException("Review ID cannot be null");
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review not found with ID: " + reviewId));

        return ReviewMapper.toDto(review);
    }

    @Override
    public List<ReviewResponseDto> getReviewsByBookId(Long bookId) {
        if (bookId == null) {
            throw new InvalidInputException("Book ID cannot be null");
        }

        if (!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found with ID: " + bookId);
        }

        List<Review> reviews = reviewRepository.findByBook_BookId(bookId);
        log.info("Found {} reviews for book ID: {}", reviews.size(), bookId);

        return reviews.stream()
                .map(ReviewMapper::toDto)
                .toList();
    }
}