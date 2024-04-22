package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

public record RoomRequestDto(
        @PositiveOrZero
        Integer number,

        @Positive
        Integer floor,

        @PositiveOrZero
        Integer capacity
) implements Serializable {
}