package com.shelfio.shelfio.mapper;

import com.shelfio.shelfio.dto.response.ReviewResponseDto;
import com.shelfio.shelfio.entity.Review;

public class ReviewMapper {

    private ReviewMapper() {
        // utility class
    }

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}