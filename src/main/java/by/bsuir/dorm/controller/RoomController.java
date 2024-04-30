package by.bsuir.dorm.controller;

import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.request.RoomRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.service.RoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<RoomDto> getAll(@Valid @Positive @RequestParam(name = "page", defaultValue = "1")  int page,
                                        @Valid @Positive @RequestParam(name = "limit", defaultValue = "15") int limit) {
        return roomService.getAll(page - 1, limit);
    }


    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @PostMapping
    public ResponseEntity<UUID> create(@Valid @RequestBody RoomRequestDto dto) {
        final UUID id = roomService.create(dto);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromPath("/api/v1/rooms/{id}")
                                .buildAndExpand(id)
                                .encode()
                                .toUri())
                .body(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoomDto update( @PathVariable("id") UUID id, @RequestBody RoomRequestDto dto) {
        return roomService.update(id, dto);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoomDto getById(@PathVariable("id") UUID id) {
        return roomService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping("/number/{number}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoomDto getByNumber(@PathVariable("number") Integer number) {
        return roomService.getByNumber(number);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        roomService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('TYPE_EMPLOYEE')")
    @GetMapping("/{id}/students")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<RoomDto.StudentDto> getStudents(@PathVariable("id") UUID id){
        return roomService.getStudentsByRoomId(id);
    }
}
