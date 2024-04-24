package by.bsuir.dorm.dto;

import java.time.Instant;

public record ExplanatoryDto(
        String recipient,
        String content,
        Instant updated
) {
}