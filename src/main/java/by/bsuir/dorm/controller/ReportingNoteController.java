package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.ReportingNoteDto;
import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.request.InternalRegulationRequestDto;
import by.bsuir.dorm.dto.request.ReportingNoteCreateRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.service.ReportsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportingNoteController {
    private final ReportsService reportsService;

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER')")
    @GetMapping("/my")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ReportingNoteDto> getAllByOwner(@Valid @Positive @RequestParam(name = "page", defaultValue = "1")  int page,
                                        @Valid @Positive @RequestParam(name = "limit", defaultValue = "15") int limit) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return reportsService.getAllByOwner(username, page - 1, limit);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER', 'ROLE_DIRECTOR')")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportingNoteDto getById(@PathVariable("id") UUID id) {
        return reportsService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER')")
    @PostMapping
    public ResponseEntity<UUID> create(@Valid @RequestBody ReportingNoteCreateRequestDto dto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final UUID id = reportsService.create(username, dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/reports/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        reportsService.deleteById(id);
    }
}
