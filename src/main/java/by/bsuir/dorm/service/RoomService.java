package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.request.RoomRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    PageResponse<RoomDto> getAll(int page, int limit);

    RoomDto getById(UUID id);

    RoomDto getByNumber(Integer number);

    UUID create(RoomRequestDto dto);

    RoomDto update(UUID id, RoomRequestDto dto);

    void deleteById(UUID id);

    List<RoomDto.StudentDto> getStudentsByRoomId(UUID id);
}
