package by.bsuir.dorm.dto.request;

import by.bsuir.dorm.validation.constraints.NotBlankIfPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MotherRegisterStudentDto(
        @NotBlank
        @Size(max = 40)
        String surname,

        @NotBlank
        @Size(max = 40)
        String name,

        @NotBlankIfPresent
        @Size(max = 40)
        String patronymic,

        @NotBlank
        @Size(max = 16)
        String phoneNumber,

        @NotBlankIfPresent
        @Size(max = 255)
        String workplace,

        @NotBlank
        @Size(min = 14, max = 14)
        String documentId
) {
}
