package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.ReportingNoteDto;
import by.bsuir.dorm.dto.request.ReportingNoteCreateRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.UUID;

public interface ReportsService {
    PageResponse<ReportingNoteDto> getAllByOwner(String username, int page, int limit);

    ReportingNoteDto getById(UUID id);

    UUID create(String creator, ReportingNoteCreateRequestDto dto);

    void deleteById(UUID id);

    void approveForDecree(String approvedBy, UUID id);
}
