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
        PassportDto passport,
        String residentialAddress,
        String phoneNumber,
        Integer roomNumber,
        RepresentativeDto representative,
        LocalDate startDate,
        LocalDate expiryDate,
        LocalDate terminationDate
) {
}
