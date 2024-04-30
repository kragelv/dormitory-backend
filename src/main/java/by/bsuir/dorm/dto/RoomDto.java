package by.bsuir.dorm.dto;

import java.util.UUID;

public record RoomDto(
        UUID id,
        Integer number,
        Integer floor,
        Integer current,
        Integer capacity
) {

    public record StudentDto(
            UUID contractId,
            UUID id,
            String cardId,
            FullNameDto fullName,
            String groupNumber
    ) {
    }
}
