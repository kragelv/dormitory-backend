package by.bsuir.dorm.dto.userpersonal;


import by.bsuir.dorm.dto.FullNameDto;
import by.bsuir.dorm.dto.RoleInUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class PersonalUserDto {
    private final String typename;
    private final UUID id;
    private final Set<RoleInUserDto> roles;
    private final FullNameDto fullName;
    private final LocalDate birthdate;
    private final String cardId;
    private final String phoneNumber;
    private final Boolean passwordNeedReset;
    private final String email;
    private final Boolean emailConfirmed;

}

