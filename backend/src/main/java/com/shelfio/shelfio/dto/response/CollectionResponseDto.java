package com.shelfio.shelfio.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CollectionResponseDto {

    private Long id;
    private String name;
    private List<BookResponseDto> books;
}
