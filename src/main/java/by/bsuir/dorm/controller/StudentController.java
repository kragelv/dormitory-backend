package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.request.RegisterStudentRequestDto;
import by.bsuir.dorm.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> registerNewStudent(@Valid @RequestBody RegisterStudentRequestDto dto) {
        final UUID id = studentService.register(dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/users/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER')")
    @PutMapping("/")
    public ResponseEntity<UUID> updateExistingStudent(@Valid @RequestBody RegisterStudentRequestDto dto) {
        final UUID id = studentService.update(dto);
        return ResponseEntity.ok()
                .location(ServletUriComponentsBuilder
                        .fromPath("/api/v1/users/{id}")
                        .buildAndExpand(id)
                        .encode()
                        .toUri())
                .body(id);
    }
}
