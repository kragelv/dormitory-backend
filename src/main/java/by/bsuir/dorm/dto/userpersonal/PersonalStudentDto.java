package by.bsuir.dorm.dto.userpersonal;

import by.bsuir.dorm.dto.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true)
public class PersonalStudentDto extends PersonalUserDto {
    RoomInStudentDto room;
    FullNameDto fullNameBy;
    Integer graduationYear;
    Double gradePointAverage;
    Boolean hasGoldMedal;
    Boolean wasInMilitary;
    Boolean isStudentUnionMember;
    String group;
    MotherDto mother;
    FatherDto father;
    String aboutCollege;
    String hobbies;


    @Builder
    public PersonalStudentDto(String typename,
                              UUID id,
                              Set<RoleInUserDto> roles,
                              FullNameDto fullName,
                              LocalDate birthdate,
                              String cardId,
                              String phoneNumber,
                              Boolean passwordNeedReset,
                              String email,
                              Boolean emailConfirmed,
                              RoomInStudentDto room,
                              FullNameDto fullNameBy,
                              Integer graduationYear,
                              Double gradePointAverage,
                              Boolean hasGoldMedal,
                              Boolean wasInMilitary,
                              Boolean isStudentUnionMember,
                              String group,
                              MotherDto mother,
                              FatherDto father,
                              String aboutCollege,
                              String hobbies
                             ) {
        super(typename, id, roles, fullName, birthdate, cardId, phoneNumber, passwordNeedReset, email, emailConfirmed);
        this.room = room;
        this.fullNameBy = fullNameBy;
        this.graduationYear = graduationYear;
        this.gradePointAverage = gradePointAverage;
        this.hasGoldMedal = hasGoldMedal;
        this.wasInMilitary = wasInMilitary;
        this.isStudentUnionMember = isStudentUnionMember;
        this.group = group;
        this.mother = mother;
        this.father = father;
        this.aboutCollege = aboutCollege;
        this.hobbies = hobbies;
    }
}