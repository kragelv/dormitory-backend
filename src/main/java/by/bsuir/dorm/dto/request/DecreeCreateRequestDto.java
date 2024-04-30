package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record DecreeCreateRequestDto(
        LocalDate date,

        @NotEmpty
        List<ReportingNoteResultDto> reportingNotes
) implements Serializable {
    public record ReportingNoteResultDto(
            @NotNull
            UUID id,

            @NotEmpty
            Map<UUID, String> violationResults
    ) implements Serializable {
    }

}
