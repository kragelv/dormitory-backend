package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDto(

        @NotBlank
        String accessToken
) {
}
