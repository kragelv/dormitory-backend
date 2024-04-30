package by.bsuir.dorm.dto.request;

import by.bsuir.dorm.validation.constraints.NotBlankIfPresent;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ContractCreateRequestDto(

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
        @Pattern(regexp = "^[A-Z]{2}$")
        String passportSeries,

        @NotBlank
        @Pattern(regexp = "^[0-9]{7}$")
        String passportNumber,

        @NotNull
        @Past
        LocalDate passportIssueDate,

        @NotBlank
        @Size(max = 255)
        String passportIssuePlace,

        @NotBlank
        @Pattern(regexp = "^[0-9]{7}[A-Z][0-9]{3}[A-Z]{2}[0-9]$")
        String passportId,

        @NotBlank
        @Size(max = 255)
        String residentialAddress,

        @NotBlank
        @Size(max = 16)
        String phoneNumber,

        @NotNull
        @PositiveOrZero
        Integer roomNumber,

        ContractCreateRepresentativeDto representative,

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
