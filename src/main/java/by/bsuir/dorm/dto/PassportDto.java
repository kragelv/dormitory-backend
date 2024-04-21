package by.bsuir.dorm.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record PassportDto(String series, String number, LocalDate issueDate, String issuePlace,
                          String passportId) implements Serializable {
}