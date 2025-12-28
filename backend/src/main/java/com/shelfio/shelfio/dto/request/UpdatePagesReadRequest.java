package com.shelfio.shelfio.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePagesReadRequest {

    @NotNull(message = "Pages read is required")
    @Min(value = 0, message = "Pages read cannot be negative")
    private Integer pagesRead;
}