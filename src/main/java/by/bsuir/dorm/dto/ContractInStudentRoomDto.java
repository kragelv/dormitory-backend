package by.bsuir.dorm.dto;

import java.time.LocalDate;

public record ContractInStudentRoomDto(
        Integer number,
        LocalDate date,
        LocalDate startDate,
        LocalDate expiryDate
) {
}
