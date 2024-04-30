package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.DecreeDto;
import by.bsuir.dorm.dto.DecreeResultDto;
import by.bsuir.dorm.dto.LeisureDto;
import by.bsuir.dorm.dto.ReportingNoteInDecreeDto;
import by.bsuir.dorm.dto.request.DecreeCreateRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface DecreeService {

    PageResponse<DecreeDto> getAll(int page, int limit, UUID createdById);

    ReportingNoteInDecreeDto getApprovedReportingNoteById(UUID id);

    DecreeDto getById(UUID id);

    UUID create(String creator, DecreeCreateRequestDto dto);

    List<DecreeResultDto> getAllResults();
}
