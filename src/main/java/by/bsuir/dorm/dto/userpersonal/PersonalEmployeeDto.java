package by.bsuir.dorm.dto.userpersonal;

import by.bsuir.dorm.dto.FullNameDto;
import by.bsuir.dorm.dto.RoleInUserDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class PersonalEmployeeDto extends PersonalUserDto {
    String residentialAddress;

    @Builder
    public PersonalEmployeeDto(String typename,
                               UUID id,
                               Set<RoleInUserDto> roles,
                               FullNameDto fullName,
                               LocalDate birthdate,
                               String cardId,
                               String phoneNumber,
                               Boolean passwordNeedReset,
                               String email,
                               Boolean emailConfirmed,
                               String residentialAddress) {
        super(typename, id, roles, fullName, birthdate, cardId, phoneNumber, passwordNeedReset, email, emailConfirmed);
        this.residentialAddress = residentialAddress;
    }
}
