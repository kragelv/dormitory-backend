package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.userpersonal.PersonalUserDto;
import by.bsuir.dorm.dto.userpublic.PublicUserDto;
import by.bsuir.dorm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PersonalUserDto getFullInfo() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getFullInfo(username);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PublicUserDto getUserById(@PathVariable UUID id) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getPublicInfoById(id);
    }

}
