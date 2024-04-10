package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequestDto(
        @NotBlank
        String token,

        @NotBlank
        String password
) {
}
