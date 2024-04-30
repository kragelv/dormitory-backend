package by.bsuir.dorm.dto;

import java.util.UUID;

public record GroupDto(
        UUID id,
        String number,
        Integer studentTotalElements
) {
}