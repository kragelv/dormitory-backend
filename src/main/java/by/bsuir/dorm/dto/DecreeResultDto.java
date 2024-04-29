package by.bsuir.dorm.dto;

import java.io.Serializable;
import java.util.UUID;

public record DecreeResultDto(
        UUID id,
        String name
) implements Serializable {
}