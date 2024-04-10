package by.bsuir.dorm.dto.response;

public record AccessResponseDto(
        String accessToken,
        boolean emailConfirmed,
        boolean passwordNeedReset
) {
}
