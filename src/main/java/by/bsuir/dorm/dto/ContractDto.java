package by.bsuir.dorm.dto;

import by.bsuir.dorm.dto.userpublic.PublicStudentDto;

import java.time.LocalDate;

public record ContractDto(
        Integer number,
        LocalDate date,
        PublicStudentDto student,
        RoomDto room,
        String cardId,
        String surname,
        String name,
        String patronymic,
        String passportSeries,
        String passportNumber,
        LocalDate passportIssueDate,
        String passportIssuePlace,
        String birthplace,
        String residentialAddress,
        Integer roomNumber,
        LocalDate startDate,
        LocalDate expiryDate,
        LocalDate terminationDate
) {
}
