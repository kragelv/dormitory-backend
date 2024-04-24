package by.bsuir.dorm.dto;

import java.util.UUID;

public record InternalRegulationDto(
        UUID id,
        String item,
        String content
) {
}
