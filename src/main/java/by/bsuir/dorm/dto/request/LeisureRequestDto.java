package by.bsuir.dorm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record LeisureRequestDto(
        @Size(max = 200)
        @NotBlank
        String title,

        @NotNull
        DayOfWeek day,

        @NotNull
        LocalTime time
) {
}