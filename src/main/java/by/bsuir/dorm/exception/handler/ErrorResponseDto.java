package by.bsuir.dorm.exception.handler;

import java.time.Instant;

public record ErrorResponseDto(
        Instant timestamp,
        int status,
        String error,
        String message
) {
}
