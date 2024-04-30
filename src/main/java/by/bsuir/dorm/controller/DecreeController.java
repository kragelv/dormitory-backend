package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.DecreeDto;
import by.bsuir.dorm.dto.DecreeResultDto;
import by.bsuir.dorm.dto.ReportingNoteInDecreeDto;
import by.bsuir.dorm.dto.request.DecreeCreateRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.service.DecreeService;
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
@RequestMapping("/api/v1/decrees")
@RequiredArgsConstructor
public class DecreeController {
    private final DecreeService decreeService;

    @PreAuthorize("hasAnyAuthority('ROLE_HEAD')")
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<DecreeDto> getAll(@Valid @Positive @RequestParam(name = "page", defaultValue = "1") int page,
                                          @Valid @Positive @RequestParam(name = "limit", defaultValue = "15") int limit,
                                          @RequestParam(name = "creator", required = false) UUID creator) {
        return decreeService.getAll(page - 1, limit, creator);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_HEAD')")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public DecreeDto getById(@PathVariable("id") UUID id) {
        return decreeService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_HEAD')")
    @GetMapping("/results")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<DecreeResultDto> getResults() {
        return decreeService.getAllResults();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_HEAD')")
    @GetMapping("/reports/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReportingNoteInDecreeDto getApprovedReportingNoteById(@PathVariable("id") UUID id) {
        return decreeService.getApprovedReportingNoteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_HEAD')")
    @PostMapping
    public ResponseEntity<UUID> create(@Valid @RequestBody DecreeCreateRequestDto dto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final UUID id = decreeService.create(username, dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/reports/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

}
