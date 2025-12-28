package com.shelfio.shelfio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReadingStatusRequest {

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(Not started|Reading|Finished)$",
            message = "Status must be one of: Not started, Reading, Finished")
    private String status;
}
