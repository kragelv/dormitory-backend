package by.bsuir.dorm.dto;

import by.bsuir.dorm.dto.userpublic.PublicStudentDto;

import java.time.LocalDate;
import java.util.UUID;

public record ContractDto(
        UUID id,
        Integer number,
        LocalDate date,
        UUID studentId,
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
