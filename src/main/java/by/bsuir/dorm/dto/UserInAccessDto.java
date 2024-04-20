package by.bsuir.dorm.dto;

import java.util.UUID;

public record UserInAccessDto(
        UUID id,
        String cardId,
        String email,
        FullNameDto fullName
) {
}
