package by.bsuir.dorm.dto.request;

import by.bsuir.dorm.util.RoleUtil;
import by.bsuir.dorm.validation.constraints.NotBlankIfPresent;
import by.bsuir.dorm.validation.constraints.Prefixed;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RegisterStudentRequestDto(
        @NotBlank
        UUID contractId,

        @Prefixed(
                value = RoleUtil.ROLE_PREFIX,
                message = "Role must be prefixed with " + RoleUtil.ROLE_PREFIX
        )
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
        @Past
        LocalDate birthdate,

        @NotBlank
        @Size(max = 255)
        String birthplace,

        @NotBlank
        @Size(max = 255)
        String residentialAddress,

        @NotBlank
        @Size(max = 16)
        String phoneNumber,

        @NotBlank
        @Size(max = 6)
        @Pattern(regexp = "[а-яА-Яa-zA-Z0-9]*", message = "Number contains invalid character")
        String groupNumber,

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

        @NotBlankIfPresent(message = "Field must be not blank")
        @Size(max = 256)
        String aboutCollege,

        @NotBlankIfPresent(message = "Field must be not blank")
        @Size(max = 256)
        String hobbies
) {
}
