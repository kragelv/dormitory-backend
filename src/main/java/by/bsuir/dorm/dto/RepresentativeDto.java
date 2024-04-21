package by.bsuir.dorm.dto;

import java.io.Serializable;
import java.util.UUID;

public record RepresentativeDto(
        UUID id,
        FullNameDto fullName,
        PassportDto passport
) implements Serializable {
}