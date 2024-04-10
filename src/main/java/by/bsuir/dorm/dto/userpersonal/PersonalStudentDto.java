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
public class PersonalStudentDto extends PersonalUserDto {
    FullNameDto fullNameBy;
    Integer graduationYear;
    Double gradePointAverage;
    Boolean hasGoldMedal;
    Boolean wasInMilitary;
    Boolean isStudentUnionMember;
    String aboutCollege;
    String hobbies;

    @Builder
    public PersonalStudentDto(String typename,
                              UUID id,
                              Set<RoleInUserDto> roles,
                              FullNameDto fullName,
                              LocalDate birthdate,
                              String cardId,
                              Boolean passwordNeedReset,
                              String email,
                              Boolean emailConfirmed,
                              FullNameDto fullNameBy,
                              Integer graduationYear,
                              Double gradePointAverage,
                              Boolean hasGoldMedal,
                              Boolean wasInMilitary,
                              Boolean isStudentUnionMember,
                              String aboutCollege,
                              String hobbies) {
        super(typename, id, roles, fullName, birthdate, cardId, passwordNeedReset, email, emailConfirmed);
        this.fullNameBy = fullNameBy;
        this.graduationYear = graduationYear;
        this.gradePointAverage = gradePointAverage;
        this.hasGoldMedal = hasGoldMedal;
        this.wasInMilitary = wasInMilitary;
        this.isStudentUnionMember = isStudentUnionMember;
        this.aboutCollege = aboutCollege;
        this.hobbies = hobbies;
    }
}
