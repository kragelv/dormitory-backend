package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmailConfirmationRequestDto(
        @NotBlank
        String token
) {
}
