package by.bsuir.dorm.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public record DecreeDto(
        UUID id,
        Integer number,
        LocalDate date,
        EmployeeDto createdBy,
        String createdByFullName
) implements Serializable {
    public record EmployeeDto(
            UUID id,
            FullNameDto fullName
    ) implements Serializable {
    }
}