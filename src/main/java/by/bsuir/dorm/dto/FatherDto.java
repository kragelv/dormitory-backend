package by.bsuir.dorm.dto;

public record FatherDto(
        FullNameDto fullName,
        String phoneNumber,
        String workplace,
        String documentId
) {
}