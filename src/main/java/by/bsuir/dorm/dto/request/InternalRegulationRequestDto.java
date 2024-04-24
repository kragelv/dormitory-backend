package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record InternalRegulationRequestDto(
        @NotBlank
        @Pattern(
                regexp = "^\\d+(\\.\\d+){0,8}$",
                message = "Invalid item format"
        )
        @Size(max = 255)
        String item,

        @NotBlank
        @Size(max = 512)
        String content
) {
}
