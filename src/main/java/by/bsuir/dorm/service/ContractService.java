package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.ContractDto;
import by.bsuir.dorm.dto.request.ContractCreateRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.UUID;

public interface ContractService {
    UUID create(String creator, ContractCreateRequestDto dto);

    PageResponse<ContractDto> getAll(int page, int limit);

    ContractDto getById(UUID id);
}
