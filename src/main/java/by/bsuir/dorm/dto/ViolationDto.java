package by.bsuir.dorm.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ViolationDto(
        UUID id,
        UUID reportingNoteId,
        Instant reportingNoteApproved,
        CreatorDto creator,
        LocalDate date,
        Instant explanatoryNoteUpdated,
        String decreeResult
) {
    public record CreatorDto(
            UUID id,
            FullNameDto fullName
    ) {
    }
}