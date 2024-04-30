package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserRequestDto(
        @NotBlank
        @Size(max = 64)
        String cardId,
        String password
) {
}
