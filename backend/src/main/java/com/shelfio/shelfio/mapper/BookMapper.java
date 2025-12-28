package com.shelfio.shelfio.mapper;

import com.shelfio.shelfio.dto.response.BookResponseDto;
import com.shelfio.shelfio.dto.response.ReviewResponseDto;
import com.shelfio.shelfio.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    private BookMapper() {
        // utility class
    }

    public static BookResponseDto toDto(Book book) {

        List<ReviewResponseDto> reviewDtos = book.getReviews() != null
                ? book.getReviews().stream()
                .map(ReviewMapper::toDto)
                .toList()
                : List.of();

        return BookResponseDto.builder()
                .id(book.getBookId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .pages(book.getPages())
                .pagesRead(book.getPagesRead())
                .publisher(book.getPublisher())
                .coverUrl(book.getBookcover())
                .category(
                book.getCategory() != null
                        ? book.getCategory().getName()
                        : null
        )
                .status(
                        book.getReadingStatus() != null
                                ? book.getReadingStatus().getStatus()
                                : null
                )
                .author(
                        book.getAuthor() != null
                                ? book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName()
                                : null
                )
                .reviews(reviewDtos)
                .build();
    }
}
