package by.bsuir.dorm.dto;

import java.util.List;

public record RoleDto(
        Integer id,
        String name,
        List<UserTypeDto> compatibleUserTypes
) {
}
