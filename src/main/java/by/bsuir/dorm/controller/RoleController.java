package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.RoleDto;
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

    @GetMapping("/type")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDto> getRolesByType(@RequestParam String name) {
        return roleService.getRolesByUserType(name);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDto> getRoles() {
        return roleService.getRolesByUserType();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoleDto getRoleById(@PathVariable Integer id) {
        return roleService.getByRoleId(id);
    }

}
