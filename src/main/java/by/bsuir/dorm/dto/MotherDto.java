package by.bsuir.dorm.dto;

public record MotherDto(
        FullNameDto fullName,
        String phoneNumber,
        String workplace,
        String documentId
) {
}