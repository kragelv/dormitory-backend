package by.bsuir.dorm.dto.request;

import by.bsuir.dorm.util.RoleUtil;
import by.bsuir.dorm.validation.constraints.NotBlankIfPresent;
import by.bsuir.dorm.validation.constraints.Prefixed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record RegisterEmployeeRequestDto(
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

        @NotNull
        @Past
        LocalDate birthdate,

        @NotBlank
        @Size(max = 16)
        String phoneNumber,

        @NotBlank
        @Size(max = 255)
        String residentialAddress,

        String password
) {
}
