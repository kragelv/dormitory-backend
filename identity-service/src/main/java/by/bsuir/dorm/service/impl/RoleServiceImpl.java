package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.EmployeeRoleRepository;
import by.bsuir.dorm.dao.RoleRepository;
import by.bsuir.dorm.dao.StudentRoleRepository;
import by.bsuir.dorm.dto.RoleDto;
import by.bsuir.dorm.dto.UserTypeDto;
import by.bsuir.dorm.dto.response.ListRolesResponseDto;
import by.bsuir.dorm.entity.Role;
import by.bsuir.dorm.entity.UserType;
import by.bsuir.dorm.exception.RoleNotFoundException;
import by.bsuir.dorm.mapper.EmployeeRoleMapper;
import by.bsuir.dorm.mapper.RoleMapper;
import by.bsuir.dorm.mapper.StudentRoleMapper;
import by.bsuir.dorm.mapper.UserTypeMapper;
import by.bsuir.dorm.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final StudentRoleRepository studentRoleRepository;
    private final EmployeeRoleRepository employeeRoleRepository;
    private final StudentRoleMapper studentRoleMapper;
    private final EmployeeRoleMapper employeeRoleMapper;
    private final RoleMapper roleMapper;
    private final UserTypeMapper userTypeMapper;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getStudentRoles() {
        return studentRoleMapper.toDto(studentRoleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getEmployeeRoles() {
        return employeeRoleMapper.toDto(employeeRoleRepository.findAll());
    }

    @Override
    public ListRolesResponseDto getRoles(UserType userType) {
        List<RoleDto> roles = switch (userType) {
            case Student -> getStudentRoles();
            case Employee -> getEmployeeRoles();
        };
        UserTypeDto userTypeDto = userTypeMapper.toDto(userType);
        return new ListRolesResponseDto(userTypeDto.type(), userTypeDto.typename(), roles);
    }

    @Override
    public List<ListRolesResponseDto> getRoles() {
        return Arrays.stream(UserType.values())
                .map(this::getRoles)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getByRoleId(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role { id = " + id + " } doesn't exist"));
        return roleMapper.toDto(role);
    }
}
