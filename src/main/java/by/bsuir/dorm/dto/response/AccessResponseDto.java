package by.bsuir.dorm.dto.response;

import by.bsuir.dorm.dto.UserInAccessDto;

public record AccessResponseDto(
        String accessToken,
        UserInAccessDto user
) {
}
