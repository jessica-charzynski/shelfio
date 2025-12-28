package com.shelfio.shelfio.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalBookDto {

    private String title;
    private String authorFirstName;
    private String authorLastName;
    private List<String> categories;
    private String isbn;
    private Integer pages;
    private String publisher;
    private String coverUrl;
}
