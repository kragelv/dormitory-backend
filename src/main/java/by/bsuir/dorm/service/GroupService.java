package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.GroupDto;
import by.bsuir.dorm.dto.request.GroupRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.UUID;

public interface GroupService {

    PageResponse<GroupDto> getAll(int page, int limit);

    GroupDto getById(UUID id);

    GroupDto getByNumber(String fullNumber);

    UUID create(GroupRequestDto dto);

    void deleteById(UUID id);

    void deleteByNumber(String number);
}
