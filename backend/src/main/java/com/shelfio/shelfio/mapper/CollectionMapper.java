package com.shelfio.shelfio.mapper;

import com.shelfio.shelfio.dto.response.BookResponseDto;
import com.shelfio.shelfio.dto.response.CollectionResponseDto;
import com.shelfio.shelfio.entity.Collection;

import java.util.List;
import java.util.stream.Collectors;

public class CollectionMapper {

    private CollectionMapper() {}

    public static CollectionResponseDto toDto(Collection collection) {

        List<BookResponseDto> books =
                collection.getBooks() == null
                        ? List.of()
                        : collection.getBooks()
                        .stream()
                        .map(BookMapper::toDto)
                        .collect(Collectors.toList());

        return CollectionResponseDto.builder()
                .id(collection.getCollectionId())
                .name(collection.getName())
                .books(books)
                .build();
    }
}
