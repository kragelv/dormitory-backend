package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.LeisureStudentDto;
import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.request.RegisterStudentRequestDto;
import by.bsuir.dorm.dto.userpersonal.PersonalStudentDto;
import by.bsuir.dorm.dto.userpublic.PublicStudentDto;
import by.bsuir.dorm.model.entity.Student;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(

        componentModel = "spring",
        uses = {
                RoleMapper.class,
                ExtensionsMapper.class
        }
)
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordNeedReset", ignore = true)
    @Mapping(target = "group", source = "group", qualifiedByName = "groupNumberToGroupRef")
    @ValueMapping(target = "email", source = MappingConstants.NULL)
    @Mapping(target = "emailConfirmed", constant = "false")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNameToRoleRef")
    @Mapping(target = "fullName.surname", source = "surname")
    @Mapping(target = "fullName.name", source = "name")
    @Mapping(target = "fullName.patronymic", source = "patronymic")
    @Mapping(target = "fullNameBy.surname", source = "surnameBy")
    @Mapping(target = "fullNameBy.name", source = "nameBy")
    @Mapping(target = "fullNameBy.patronymic", source = "patronymicBy")
    @Mapping(target = "mother.fullName.name", source = "mother.name")
    @Mapping(target = "mother.fullName.surname", source = "mother.surname")
    @Mapping(target = "mother.fullName.patronymic", source = "mother.patronymic")
    @Mapping(target = "father.fullName.name", source = "father.name")
    @Mapping(target = "father.fullName.surname", source = "father.surname")
    @Mapping(target = "father.fullName.patronymic", source = "father.patronymic")
    Student toEntity(RegisterStudentRequestDto dto);

    @Mapping(target = "group", source = "group.number")
    @Mapping(target = "room", source = ".", qualifiedByName = "getStudentRoom")
    PersonalStudentDto toPersonalDto(Student student);

    @Mapping(target = "group", source = "group.number")
    PublicStudentDto toPublicDto(Student student);

    @Mapping(target = "roomNumber", source = ".", qualifiedByName = "getStudentRoomNumber")
    LeisureStudentDto toLeisureStudentDto(Student student);

    @Mapping(target = "groupNumber", source = "student.group.number")
    @Mapping(target = "contractId", source = "contractId")
    RoomDto.StudentDto toRoomStudentDto(Student student, UUID contractId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "toEntity")
    Student partialUpdate(RegisterStudentRequestDto registerStudentRequestDto, @MappingTarget Student student);
}
