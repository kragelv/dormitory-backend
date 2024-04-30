package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.ExplanatoryDto;
import by.bsuir.dorm.dto.ViolationDto;
import by.bsuir.dorm.dto.request.ExplanatoryRequestDto;

import java.util.UUID;

public interface ExplanatoryService {

    ExplanatoryDto create(String username, UUID id, ExplanatoryRequestDto dto);

    ExplanatoryDto update(String username, UUID id, ExplanatoryRequestDto dto);

    ExplanatoryDto get(String username, UUID id);
}
