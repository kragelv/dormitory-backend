package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.request.RegisterEmployeeRequestDto;
import by.bsuir.dorm.dto.userpersonal.PersonalEmployeeDto;
import by.bsuir.dorm.dto.userpublic.PublicEmployeeDto;
import by.bsuir.dorm.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
        componentModel = "spring",
        uses = {ExtensionsMapper.class}
)
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordNeedReset", ignore = true)
    @ValueMapping(target = "email", source = MappingConstants.NULL)
    @Mapping(target = "emailConfirmed", constant = "false")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNameToRoleRef")
    @Mapping(target = "fullName.surname", source = "surname")
    @Mapping(target = "fullName.name", source = "name")
    @Mapping(target = "fullName.patronymic", source = "patronymic")
    @Mapping(target = "phone.number", source = "phoneNumber")
    Employee toEntity(RegisterEmployeeRequestDto dto);

    @Mapping(target = "phoneNumber", source = "phone.number")
    PersonalEmployeeDto toPersonalDto(Employee employee);

    PublicEmployeeDto toPublicDto(Employee employee);
}
