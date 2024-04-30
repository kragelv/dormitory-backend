package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.ExplanatoryDto;
import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.ViolationDto;
import by.bsuir.dorm.dto.request.ExplanatoryRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.UUID;

public interface ViolationService {
    PageResponse<ViolationDto> getAllMy(String username, int page, int limit);

    ViolationDto getById(String username, UUID id);
}
