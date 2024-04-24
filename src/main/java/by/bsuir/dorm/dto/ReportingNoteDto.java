package by.bsuir.dorm.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReportingNoteDto(
        UUID id,
        LocalDate date,
        Instant approved,
        List<StudentViolationDto> violations,
        DecreeDto decree) {
    public record StudentViolationDto(
            UUID studentId,
            FullNameDto studentFullName,
            LocalDate date,
            String content
    ) {
    }

    public record DecreeDto(
            UUID id,
            Integer number,
            LocalDate date
    ) {
    }
}