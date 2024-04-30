package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.ExplanatoryDto;
import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.ViolationDto;
import by.bsuir.dorm.dto.request.ExplanatoryRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.service.ExplanatoryService;
import by.bsuir.dorm.service.ViolationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/violations")
@RequiredArgsConstructor
public class ViolationController {
    private final ViolationService violationService;
    private final ExplanatoryService explanatoryService;

    @PreAuthorize("hasAnyAuthority('TYPE_STUDENT')")
    @GetMapping("/my")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ViolationDto> getAllMy(@Valid @Positive @RequestParam(name = "page", defaultValue = "1")  int page,
                                               @Valid @Positive @RequestParam(name = "limit", defaultValue = "15") int limit) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return violationService.getAllMy(username, page - 1, limit);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_STUDENT')")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ViolationDto getById(@PathVariable("id") UUID id) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return violationService.getById(username, id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER', 'ROLE_DIRECTOR', 'TYPE_STUDENT')")
    @GetMapping("/{id}/explanatory")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ExplanatoryDto getExplanatory(@PathVariable("id") UUID id) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return explanatoryService.get(username, id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_STUDENT')")
    @PostMapping("/{id}/explanatory")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ExplanatoryDto createExplanatory(@PathVariable("id") UUID id, @Valid @RequestBody ExplanatoryRequestDto dto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return explanatoryService.create(username, id, dto);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_STUDENT')")
    @PutMapping("/{id}/explanatory")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ExplanatoryDto updateExplanatory(@PathVariable("id") UUID id, @Valid @RequestBody ExplanatoryRequestDto dto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return explanatoryService.update(username, id, dto);
    }
}
