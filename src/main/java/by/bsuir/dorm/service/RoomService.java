package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.UUID;

public interface RoomService {
    PageResponse<RoomDto> getAll(int page, int limit);

    RoomDto getById(UUID id);

    RoomDto getByNumber(Integer number);
}
