package by.bsuir.dorm.dto.response;

public record AccessResponseDto(
        String accessToken,
        Boolean emailConfirmed,
        Boolean passwordNeedReset
) {
}
