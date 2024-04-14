package by.bsuir.dorm.dto;

import java.util.UUID;

public record RoomDto(
        UUID id,
        Integer number,
        Integer capacity
) {
}
