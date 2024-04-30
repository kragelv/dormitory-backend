package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.RoleDto;
import by.bsuir.dorm.model.entity.Role;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserTypeMapper.class})
public interface RoleMapper {

    Role toEntity(String name);

    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);

    List<RoleDto> toDto(Collection<Role> role);
}
