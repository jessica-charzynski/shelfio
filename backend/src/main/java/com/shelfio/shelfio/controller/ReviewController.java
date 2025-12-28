package com.shelfio.shelfio.controller;

import com.shelfio.shelfio.dto.request.AddReviewRequest;
import com.shelfio.shelfio.dto.request.UpdateReviewRequest;
import com.shelfio.shelfio.dto.response.ApiResponse;
import com.shelfio.shelfio.dto.response.ReviewResponseDto;
import com.shelfio.shelfio.service.ReviewService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReview(@PathVariable Long reviewId) {
        log.info("Request to get review with ID: {}", reviewId);
        ReviewResponseDto review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(ApiResponse.success(review));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getReviewsByBook(
            @PathVariable Long bookId) {
        log.info("Request to get reviews for book ID: {}", bookId);
        List<ReviewResponseDto> reviews = reviewService.getReviewsByBookId(bookId);
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequest request) {

        log.info("Request to update review ID: {} with rating: {}",
                reviewId, request.getRating());

        ReviewResponseDto review = reviewService.updateReview(
                reviewId,
                request.getRating(),
                request.getComment());

        return ResponseEntity.ok(ApiResponse.success("Review updated successfully", review));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId) {
        log.info("Request to delete review with ID: {}", reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(ApiResponse.success("Review deleted successfully", null));
    }
}