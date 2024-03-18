package by.bsuir.dorm.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class UserDto {
    private final UUID id;
    private final Set<RoleDto> roles;
    private final FullNameDto fullName;
    private final LocalDate birthdate;
    private final String cardId;
}

