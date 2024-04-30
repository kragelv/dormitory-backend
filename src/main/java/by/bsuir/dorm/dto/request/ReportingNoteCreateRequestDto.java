package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReportingNoteCreateRequestDto(
        LocalDate date,
        @NotEmpty
        List<StudentViolationDto> violations
) {
    public record StudentViolationDto(
            @NotNull
            UUID studentId,
            @NotNull
            UUID regulationId,
            LocalDate date,
            @NotBlank
            @Size(max = 512)
            String content
    ) {
    }
}