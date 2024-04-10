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
public class PublicStudentDto extends PublicUserDto {
    FullNameDto fullNameBy;
    Integer graduationYear;
    Boolean isStudentUnionMember;

    @Builder
    public PublicStudentDto(String typename,
                            UUID id,
                            Set<RoleInUserDto> roles,
                            FullNameDto fullName,
                            LocalDate birthdate,
                            String cardId,
                            FullNameDto fullNameBy,
                            Integer graduationYear,
                            Boolean isStudentUnionMember) {
        super(typename, id, roles, fullName, birthdate, cardId);
        this.fullNameBy = fullNameBy;
        this.graduationYear = graduationYear;
        this.isStudentUnionMember = isStudentUnionMember;
    }
}
