package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExplanatoryRequestDto(
        @NotBlank
        @Size(max = 128)
        String recipient,

        @NotBlank
        @Size(max = 512)
        String content
) {
}
