package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.GroupDto;
import by.bsuir.dorm.dto.request.GroupRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.service.GroupService;
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
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<GroupDto> getAll(@Valid @Positive @RequestParam(name = "page", defaultValue = "1")  int page,
                                         @Valid @Positive @RequestParam(name = "limit", defaultValue = "15") int limit) {
        return groupService.getAll(page - 1, limit);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public GroupDto getById(@PathVariable("id") UUID id) {
        return groupService.getById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/number/{number}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public GroupDto getByNumber(@PathVariable("number") String number) {
        return groupService.getByNumber(number);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> createGroup(@Valid @RequestBody GroupRequestDto dto) {
        final UUID id = groupService.create(dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/groups/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") UUID id) {
        groupService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CARETAKER')")
    @DeleteMapping("number/{number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByNumber(@PathVariable("number") String number) {
        groupService.deleteByNumber(number);
    }
}
