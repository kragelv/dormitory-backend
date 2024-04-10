package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.userpersonal.PersonalEmployeeDto;
import by.bsuir.dorm.dto.userpublic.PublicEmployeeDto;
import by.bsuir.dorm.model.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {RoleMapper.class}
)
public interface EmployeeMapper {
    PersonalEmployeeDto toPersonalDto(Employee employee);

    PublicEmployeeDto toPublicDto(Employee employee);
}
