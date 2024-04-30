package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.LeisureDto;
import by.bsuir.dorm.dto.LeisureStudentDto;
import by.bsuir.dorm.dto.request.LeisureRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface LeisureService {
    PageResponse<LeisureDto> getAll(int page, int limit, UUID organizer, UUID student);

    LeisureDto getById(UUID id);

    UUID create(String creator, LeisureRequestDto dto);

    void deleteById(UUID id);

    List<LeisureStudentDto> getStudentsByLeisureId(UUID id);

    void studentJoin(String username, UUID leisureId);

    void studentLeave(String username, UUID leisureId);

    boolean isParticipant(String username, UUID leisureId);
}
