package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.InternalRegulationDto;
import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.request.InternalRegulationRequestDto;
import by.bsuir.dorm.dto.request.RoomRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.dao.InternalRegulationRepository;
import by.bsuir.dorm.service.RegulationService;
import by.bsuir.dorm.validation.constraints.NotBlankIfPresent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/regulations")
@RequiredArgsConstructor
public class InternalRegulationController {
    private final RegulationService regulationService;

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<InternalRegulationDto> getAll(@Valid @Positive @RequestParam(name = "page", defaultValue = "1")  int page,
                                                      @Valid @Positive @RequestParam(name = "limit", defaultValue = "15") int limit,
                                                      @NotBlankIfPresent @RequestParam(name = "search", required = false) String search) {
        return  regulationService.getAll(page - 1, limit, search);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InternalRegulationDto getById(@PathVariable("id") UUID id) {
        return regulationService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @PostMapping
    public ResponseEntity<UUID> create(@Valid @RequestBody InternalRegulationRequestDto dto) {
        final UUID id = regulationService.create(dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/regulations/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InternalRegulationDto update(@PathVariable("id") UUID id,
                                        @RequestBody InternalRegulationRequestDto dto) {
        return regulationService.update(id, dto);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        regulationService.deleteById(id);
    }

}
