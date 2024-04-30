package by.bsuir.dorm.dto;

import java.util.UUID;

public record LeisureStudentDto(
        UUID id,
        String groupNumber,
        FullNameDto fullName,
        String phoneNumber,
        Integer roomNumber
) {
}