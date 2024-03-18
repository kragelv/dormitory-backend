package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.RoleDto;
import by.bsuir.dorm.entity.Role;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role employeeRole);

    List<RoleDto> toDto(Collection<Role> employeeRole);
}
