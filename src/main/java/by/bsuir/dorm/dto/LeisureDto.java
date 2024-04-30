package by.bsuir.dorm.dto;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record LeisureDto(
        UUID id,
        String title,
        DayOfWeek day,
        LocalTime time,
        EmployeeDto organizer,
        Integer studentTotalElements
) {
    public record EmployeeDto(UUID id, FullNameDto fullName) {
    }
}