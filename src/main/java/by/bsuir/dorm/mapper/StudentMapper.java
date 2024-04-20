package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.userpersonal.PersonalStudentDto;
import by.bsuir.dorm.dto.userpublic.PublicStudentDto;
import by.bsuir.dorm.dto.request.RegisterStudentRequestDto;
import by.bsuir.dorm.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = "spring",
        uses = {RoleMapper.class, ExtensionsMapper.class}
)
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordNeedReset", ignore = true)
    @Mapping(target = "group", ignore = true)
    @ValueMapping(target = "email", source = MappingConstants.NULL)
    @Mapping(target = "emailConfirmed", constant = "false")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNameToRoleRef")
    @Mapping(target = "fullName.surname", source = "surname")
    @Mapping(target = "fullName.name", source = "name")
    @Mapping(target = "fullName.patronymic", source = "patronymic")
    @Mapping(target = "fullNameBy.surname", source = "surnameBy")
    @Mapping(target = "fullNameBy.name", source = "nameBy")
    @Mapping(target = "fullNameBy.patronymic", source = "patronymicBy")
    Student toEntity(RegisterStudentRequestDto dto);

    PersonalStudentDto toPersonalDto(Student student);

    PublicStudentDto toPublicDto(Student student);
}
