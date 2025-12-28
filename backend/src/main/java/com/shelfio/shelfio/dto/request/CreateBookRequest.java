package com.shelfio.shelfio.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Author first name is required")
    @Size(max = 100, message = "Author first name must not exceed 100 characters")
    private String authorFirstName;

    @NotBlank(message = "Author last name is required")
    @Size(max = 100, message = "Author last name must not exceed 100 characters")
    private String authorLastName;

    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;

    @NotBlank(message = "Status is required")
    private String status;

    @Min(value = 1, message = "Pages must be at least 1")
    private Integer pages;

    @Size(max = 20, message = "ISBN must not exceed 20 characters")
    private String isbn;

    @Size(max = 255, message = "Publisher must not exceed 255 characters")
    private String publisher;

    @Size(max = 500, message = "Cover URL must not exceed 500 characters")
    private String coverUrl;
}