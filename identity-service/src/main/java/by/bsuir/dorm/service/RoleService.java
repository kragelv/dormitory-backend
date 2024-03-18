package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.RoleDto;
import by.bsuir.dorm.dto.response.ListRolesResponseDto;
import by.bsuir.dorm.entity.UserType;

import java.util.List;

public interface RoleService {
    List<RoleDto> getStudentRoles();

    List<RoleDto> getEmployeeRoles();

    ListRolesResponseDto getRoles(UserType userType);

    List<ListRolesResponseDto> getRoles();

    RoleDto getByRoleId(Integer id);
}
