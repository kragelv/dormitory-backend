package by.bsuir.dorm.dto.userpublic;

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
public class PublicEmployeeDto extends PublicUserDto {

    @Builder
    public PublicEmployeeDto(String typename,
                             UUID id,
                             Set<RoleInUserDto> roles,
                             FullNameDto fullName,
                             LocalDate birthdate,
                             String cardId) {
        super(typename, id, roles, fullName, birthdate, cardId);
    }
}
