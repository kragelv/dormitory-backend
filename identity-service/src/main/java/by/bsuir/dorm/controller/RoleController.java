package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.RoleDto;
import by.bsuir.dorm.dto.response.ListRolesResponseDto;
import by.bsuir.dorm.entity.UserType;
import by.bsuir.dorm.exception.InvalidUserTypeException;
import by.bsuir.dorm.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/types/{type}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ListRolesResponseDto getRolesByType(
            @PathVariable Integer type
    ) {
        UserType userType = UserType.fromIntValue(type)
                .orElseThrow(() -> new InvalidUserTypeException("Parameter 'type' with value '"
                        + type + "' is invalid"));
        return roleService.getRoles(userType);
    }

    @GetMapping("/types")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ListRolesResponseDto> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoleDto getRoleById(
            @PathVariable Integer id
    ) {
        return roleService.getByRoleId(id);
    }

}
