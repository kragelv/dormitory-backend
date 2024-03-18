package by.bsuir.dorm.dto.response;

import by.bsuir.dorm.dto.RoleDto;

import java.util.List;

public record ListRolesResponseDto(
        Integer type,
        String typename,
        List<RoleDto> roles
) {
}
