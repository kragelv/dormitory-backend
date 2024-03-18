package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.RoleDto;
import by.bsuir.dorm.entity.EmployeeRole;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeRoleMapper {
    EmployeeRole toEntity(RoleDto roleDto);

    RoleDto toDto(EmployeeRole employeeRole);

    List<EmployeeRole> toEntity(Collection<RoleDto> roleDto);

    List<RoleDto> toDto(Collection<EmployeeRole> employeeRole);
}
