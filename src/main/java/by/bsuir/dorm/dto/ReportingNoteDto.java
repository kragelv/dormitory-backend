package by.bsuir.dorm.dto;

import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReportingNoteDto(
        UUID id,
        LocalDate date,
        Instant approved,
        List<StudentViolationDto> violations) {
    public record StudentViolationDto(
            UUID studentId,
            FullNameDto studentFullName,
            LocalDate date,
            String content,
            Instant explanatoryNoteUpdated
    ) {
    }
}