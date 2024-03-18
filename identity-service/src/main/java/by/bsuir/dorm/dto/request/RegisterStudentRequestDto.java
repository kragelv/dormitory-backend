package by.bsuir.dorm.dto.request;

import by.bsuir.dorm.validation.NotBlankIfPresent;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record RegisterStudentRequestDto(
        String password,
        List<String> roles,

        @NotBlank
        @Size(max = 64)
        String cardId,

        @NotBlank
        @Size(max = 40)
        String surname,

        @NotBlank
        @Size(max = 40)
        String name,

        @NotBlankIfPresent
        @Size(max = 40)
        String patronymic,

        @NotNull
        @Past
        LocalDate birthdate,

        @NotBlankIfPresent
        @Size(max = 40)
        String surnameBy,

        @NotBlankIfPresent
        @Size(max = 40)
        String nameBy,

        @NotBlankIfPresent
        @Size(max = 40)
        String patronymicBy,

        @NotNull
        @Min(1900)
        Integer graduationYear,

        @NotNull
        Double gradePointAverage,

        @NotNull
        Boolean hasGoldMedal,

        @NotNull
        Boolean wasInMilitary,

        @NotNull
        Boolean isStudentUnionMember,

        @NotBlankIfPresent
        @Size(max = 256)
        String aboutCollege,

        @NotBlankIfPresent
        @Size(max = 256)
        String hobbies
) {
}
