package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.RoleDto;
import by.bsuir.dorm.entity.StudentRole;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentRoleMapper {
    StudentRole toEntity(RoleDto roleDto);

    RoleDto toDto(StudentRole studentRole);

    List<StudentRole> toEntity(Collection<RoleDto> roleDto);

    List<RoleDto> toDto(Collection<StudentRole> studentRole);
}

