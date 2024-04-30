package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.InternalRegulationDto;
import by.bsuir.dorm.dto.request.InternalRegulationRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.UUID;

public interface RegulationService {
    PageResponse<InternalRegulationDto> getAll(int page, int limit, String search);

    UUID create(InternalRegulationRequestDto dto);

    InternalRegulationDto update(UUID id, InternalRegulationRequestDto dto);

    void deleteById(UUID id);

    InternalRegulationDto getById(UUID id);
}
