package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getRolesByUserType(String typename);

    List<RoleDto> getRoles();

    RoleDto getByRoleId(Integer id);
}
