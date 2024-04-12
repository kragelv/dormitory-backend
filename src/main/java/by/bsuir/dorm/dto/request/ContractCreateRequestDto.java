package by.bsuir.dorm.dto.request;

import by.bsuir.dorm.validation.constraints.NotBlankIfPresent;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ContractCreateRequestDto(
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

        @NotBlank
        @Size(max = 2)
        String passportSeries,

        @NotBlank
        @Size(max = 7)
        String passportNumber,

        @NotNull
        @Past
        LocalDate passportIssueDate,

        @NotBlank
        @Size(max = 255)
        String passportIssuePlace,

        @NotBlank
        @Size(max = 255)
        String birthplace,

        @NotBlank
        @Size(max = 255)
        String residentialAddress,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate expiryDate
) {
        @AssertTrue( message = "Field 'expiryDate' should be later than 'startDate'")
        public boolean isExpiryAfterStart() {
                return expiryDate.isAfter(startDate);
        }
}
