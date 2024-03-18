package by.bsuir.dorm.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Value
@Getter
@EqualsAndHashCode(callSuper = true)
//without Dto postfix for compatibility with graphql schema type naming
public class StudentDto extends UserDto {
    FullNameDto fullNameBy;
    Integer graduationYear;
    Double gradePointAverage;
    Boolean hasGoldMedal;
    Boolean wasInMilitary;
    Boolean isStudentUnionMember;
    String aboutCollege;
    String hobbies;

    @Builder

    public StudentDto(UUID id,
                      Set<RoleDto> roles,
                      FullNameDto fullName,
                      LocalDate birthdate,
                      String cardId,
                      FullNameDto fullNameBy,
                      Integer graduationYear,
                      Double gradePointAverage,
                      Boolean hasGoldMedal,
                      Boolean wasInMilitary,
                      Boolean isStudentUnionMember,
                      String aboutCollege,
                      String hobbies) {
        super(id, roles, fullName, birthdate, cardId);
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
