package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.LeisureDto;
import by.bsuir.dorm.dto.LeisureStudentDto;
import by.bsuir.dorm.dto.request.LeisureRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.service.LeisureService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/leisures")
@RequiredArgsConstructor
public class LeisureController {
    private final LeisureService leisureService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<LeisureDto> getAll(@Valid @Positive @RequestParam(name = "page", defaultValue = "1") int page,
                                           @Valid @Positive @RequestParam(name = "limit", defaultValue = "15") int limit,
                                           @RequestParam(name = "organizer", required = false) UUID organizer,
                                           @RequestParam(name = "student", required = false) UUID student) {
        return leisureService.getAll(page - 1, limit, organizer, student);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE', 'TYPE_STUDENT')")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public LeisureDto getById(@PathVariable("id") UUID id) {
        return leisureService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> create(@Valid @RequestBody LeisureRequestDto dto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final UUID id = leisureService.create(username, dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/leisures/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        leisureService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_STUDENT')")
    @GetMapping("/{id}/is-participant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public boolean isParticipant(@PathVariable("id") UUID id) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return leisureService.isParticipant(username, id);
    }



    @PreAuthorize("hasAnyAuthority('TYPE_STUDENT')")
    @PostMapping("/{id}/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void studentJoin(@PathVariable("id") UUID id) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        leisureService.studentJoin(username, id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_STUDENT')")
    @PostMapping("/{id}/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void studentLeave(@PathVariable("id") UUID id) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        leisureService.studentLeave(username, id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping("/{id}/students")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<LeisureStudentDto> getStudentsByLeisureId(@PathVariable("id") UUID id) {
        return leisureService.getStudentsByLeisureId(id);
    }
}
