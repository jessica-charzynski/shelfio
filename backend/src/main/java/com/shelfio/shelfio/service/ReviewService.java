package com.shelfio.shelfio.service;

import com.shelfio.shelfio.dto.response.ReviewResponseDto;

import java.util.List;

public interface ReviewService {

    ReviewResponseDto addReview(Long bookId, Integer rating, String comment);

    ReviewResponseDto updateReview(Long reviewId, Integer rating, String comment);

    void deleteReview(Long reviewId);

    ReviewResponseDto getReviewById(Long reviewId);

    List<ReviewResponseDto> getReviewsByBookId(Long bookId);
}