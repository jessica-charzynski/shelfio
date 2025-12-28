package com.shelfio.shelfio.dto.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {

    private Long id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private String status;
    private Integer pages;
    private Integer pagesRead;
    private String publisher;
    private String coverUrl;
    private List<ReviewResponseDto> reviews;
}
