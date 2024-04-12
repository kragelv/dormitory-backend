package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.request.LoginUserRequestDto;
import by.bsuir.dorm.dto.request.RefreshTokenRequestDto;
import by.bsuir.dorm.dto.request.RegisterEmployeeRequestDto;
import by.bsuir.dorm.dto.response.AccessResponseDto;
import by.bsuir.dorm.service.AuthService;
import by.bsuir.dorm.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmployeeService employeeService;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/register-employee")
    public ResponseEntity<UUID> registerEmployee(@Valid @RequestBody RegisterEmployeeRequestDto dto) {
        final UUID id = employeeService.register(dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/users/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AccessResponseDto login(@Valid @RequestBody LoginUserRequestDto dto,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        return authService.login(dto, request, response);
    }

    @PostMapping("/refresh-token")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AccessResponseDto refreshToken(@Valid @RequestBody RefreshTokenRequestDto dto,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        return authService.refresh(dto, request, response);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {
        authService.logout(request, response);
    }
}
