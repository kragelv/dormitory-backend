package by.bsuir.dorm.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;


public record ReportingNoteInDecreeDto(
        UUID id,
        CaretakerDto caretaker,
        LocalDate date,
        Set<StudentViolationDto> violations
) implements Serializable {

    public record CaretakerDto(
            UUID id,
            FullNameDto fullName
    ) implements Serializable {
    }

    public record StudentViolationDto(
            UUID id,
            StudentDto student,
            ReportingNoteDto reportingNote,
            InternalRegulationDto internalRegulation,
            LocalDate date,
            String content,
            ExplanatoryDto explanatory
    ) implements Serializable {
        public record StudentDto(
                UUID id,
                String cardId,
                FullNameDto fullName
        ) implements Serializable {
        }

        public record ReportingNoteDto(
                UUID id,
                LocalDate date,
                Instant approved
        ) implements Serializable {
        }

        public record InternalRegulationDto(
                UUID id,
                String content,
                String itemString
        ) implements Serializable {
        }
    }
}