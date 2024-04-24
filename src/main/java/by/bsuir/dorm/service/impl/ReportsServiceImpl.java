package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.ReportingNoteRepository;
import by.bsuir.dorm.dto.ReportingNoteDto;
import by.bsuir.dorm.dto.request.ReportingNoteCreateRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.ReportingNoteNotFoundException;
import by.bsuir.dorm.exception.ReportingNoteStateException;
import by.bsuir.dorm.mapper.ReportingNoteMapper;
import by.bsuir.dorm.model.entity.Employee;
import by.bsuir.dorm.model.entity.ReportingNote;
import by.bsuir.dorm.service.ReportsService;
import by.bsuir.dorm.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportsServiceImpl implements ReportsService {
    private final ReportingNoteRepository reportingNoteRepository;
    private final UserSecurityService userSecurityService;
    private final ReportingNoteMapper reportingNoteMapper;

    @Override
    public PageResponse<ReportingNoteDto> getAllByOwner(String username, int page, int limit) {
        final Employee owner = userSecurityService.findEmployeeByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee { id = " + username + " } not found"));
        final Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "date"));
        final Page<ReportingNote> pageResult = reportingNoteRepository.findAllByCaretaker(owner, pageable);
        log.info("Get reports [owner = " + username + ", page = " + page + ", limit = " + limit + "] : totalElements = "
                + pageResult.getTotalElements() + ", numberOfElements = " + pageResult.getNumberOfElements());
        return PageResponse.create(pageResult, reportingNoteMapper::toDto);
    }

    @Override
    public ReportingNoteDto getById(UUID id) {
        final ReportingNote reportingNote = reportingNoteRepository.findById(id)
                .orElseThrow(() -> new ReportingNoteNotFoundException("Reporting note { id = " + id + " } doesn't exist"));
        log.info("Get report by id: " + id);
        return reportingNoteMapper.toDto(reportingNote);
    }

    @Override
    public UUID create(String creator, ReportingNoteCreateRequestDto dto) {
        final Employee caretaker = userSecurityService.findEmployeeByUsername(creator)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid creator id = " + creator));
        final ReportingNote reportingNote = reportingNoteMapper.toEntity(dto);
        reportingNote.setCaretaker(caretaker);
        final ReportingNote saved = reportingNoteRepository.save(reportingNote);
        log.info("Create Reporting note { id = " + saved.getId() + ", caretaker = { id = " + creator + ", name = " + caretaker.getFullName() + "} }");
        return saved.getId();
    }

    @Override
    public void deleteById(UUID id) {
        reportingNoteRepository.findById(id).ifPresent(reportingNote -> {
            if (reportingNote.getApproved() != null || reportingNote.getDecree() != null) {
                throw new ReportingNoteStateException("Reporting note { id = " + id + " } can't be deleted");
            }
            reportingNoteRepository.delete(reportingNote);
            log.info("Delete Reporting note { id = " + id + " }");
        });
    }

    @Override
    public void approveForDecree(String approvedBy, UUID id) {
        final Employee employee = userSecurityService.findEmployeeByUsername(approvedBy)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid employee id = " + approvedBy));
        final ReportingNote reportingNote = reportingNoteRepository.findById(id)
                .orElseThrow(() -> new ReportingNoteNotFoundException("Reporting note { id = " + id + " } doesn't exist"));
        if (employee.getId() != reportingNote.getCaretaker().getId()) {
            throw new AccessDeniedException("Invalid resource owner");
        }
        if (reportingNote.getDecree() != null) {
            throw new ReportingNoteStateException("Reporting note { id = " + id + " } has decree");
        }
        reportingNote.setApproved(Instant.now());
        reportingNoteRepository.save(reportingNote);
        log.info("Approve for decree Reporting note { id = " + id + " }");
    }
}
