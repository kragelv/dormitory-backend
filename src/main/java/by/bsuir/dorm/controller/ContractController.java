package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.ContractDto;
import by.bsuir.dorm.dto.request.ContractCreateRequestDto;
import by.bsuir.dorm.dto.request.ContractFilter;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.service.ContractService;
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
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PreAuthorize("hasAnyRole('ROLE_HEAD')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> createContract(@Valid @RequestBody ContractCreateRequestDto dto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final UUID id = contractService.create(username, dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/contracts/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ContractDto> getAll(@Valid @Positive @RequestParam(name = "page", defaultValue = "1") int page,
                                            @Valid @Positive @RequestParam(name = "limit", defaultValue = "15") int limit,
                                            @RequestParam(name = "filter", defaultValue = "ALL") ContractFilter filter
    ) {
        return contractService.getAll(page - 1, limit, filter);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ContractDto getById(@PathVariable("id") UUID id) {
        return contractService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @PostMapping("/{id}/terminate")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void terminateContract(@PathVariable("id") UUID id) {
        contractService.terminate(id);
    }
}
