package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GroupRequestDto(
        @NotBlank
        @Size(max = 6)
        @Pattern(regexp = "[а-яА-Яa-zA-Z0-9]*", message = "Number contains invalid character")
        String number
) {
}
