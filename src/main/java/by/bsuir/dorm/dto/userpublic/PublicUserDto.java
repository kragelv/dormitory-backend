package by.bsuir.dorm.dto.userpublic;


import by.bsuir.dorm.dto.FullNameDto;
import by.bsuir.dorm.dto.RoleInUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class PublicUserDto {
    private final String typename;
    private final UUID id;
    private final Set<RoleInUserDto> roles;
    private final FullNameDto fullName;
    private final LocalDate birthdate;
    private final String cardId;
}

