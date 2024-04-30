package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.DecreeRepository;
import by.bsuir.dorm.dao.DecreeResultRepository;
import by.bsuir.dorm.dao.ReportingNoteRepository;
import by.bsuir.dorm.dto.DecreeDto;
import by.bsuir.dorm.dto.DecreeResultDto;
import by.bsuir.dorm.dto.ReportingNoteInDecreeDto;
import by.bsuir.dorm.dto.request.DecreeCreateRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.DecreeNotFoundException;
import by.bsuir.dorm.exception.DecreeStateException;
import by.bsuir.dorm.exception.InvalidDecreeDataException;
import by.bsuir.dorm.exception.ReportingNoteNotFoundException;
import by.bsuir.dorm.mapper.DecreeMapper;
import by.bsuir.dorm.mapper.DecreeResultMapper;
import by.bsuir.dorm.mapper.ReportingNoteMapper;
import by.bsuir.dorm.model.entity.*;
import by.bsuir.dorm.service.DecreeService;
import by.bsuir.dorm.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DecreeServiceImpl implements DecreeService {
    private final DecreeResultRepository decreeResultRepository;
    private final DecreeResultMapper decreeResultMapper;
    private final ReportingNoteRepository reportingNoteRepository;
    private final ReportingNoteMapper reportingNoteMapper;
    private final DecreeRepository decreeRepository;
    private final DecreeMapper decreeMapper;
    private final UserSecurityService userSecurityService;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DecreeDto> getAll(int page, int limit, UUID createdById) {
        final Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "date"));
        final Page<Decree> pageResult;
        if (createdById == null) {
            pageResult = decreeRepository.findAll(pageable);
        } else {
            pageResult = decreeRepository.findAllByCreatedBy(createdById, pageable);
        }
        log.info("Get regulations [page = " + page + ", limit = " + limit + ", createdById = " + createdById + "] : totalElements = "
                + pageResult.getTotalElements() + ", numberOfElements = " + pageResult.getNumberOfElements());
        return PageResponse.create(pageResult, decreeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportingNoteInDecreeDto getApprovedReportingNoteById(UUID id) {
        final ReportingNote reportingNote = reportingNoteRepository.findByIdAndApprovedIsNotNull(id)
                .orElseThrow(() -> new ReportingNoteNotFoundException("Approved reporting note { id = " + id + " } doesn't exist"));
        log.info("Get approved reporting note by id: " + id);
        return reportingNoteMapper.toInDecreeDto(reportingNote);
    }

    @Override
    @Transactional(readOnly = true)
    public DecreeDto getById(UUID id) {
        final Decree decree = decreeRepository.findById(id)
                .orElseThrow(() -> new DecreeNotFoundException("Decree { id = " + id + " } doesn't exist"));
        log.info("Get decree by id: " + id);
        return decreeMapper.toDto(decree);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DecreeResultDto> getAllResults() {
        final List<DecreeResult> results = decreeResultRepository.findAll();
        log.info("Get all results { size = " + results.size() + " }");
        return results.stream()
                .map(decreeResultMapper::toDto)
                .toList();
    }

    @Override
    public UUID create(String creator, DecreeCreateRequestDto dto) {
        final Employee employee = userSecurityService.findEmployeeByUsername(creator)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid creator id = " + creator));
        final Decree decree = new Decree();
        decree.setCreatedBy(employee);
        decree.setCreatedByFullName(employee.getFullName().toString());
        decree.setNumber(0);
        if (dto.date() != null) {
            decree.setDate(dto.date());
        } else {
            decree.setDate(LocalDate.now());
        }
        for (DecreeCreateRequestDto.ReportingNoteResultDto reportingNoteDto : dto.reportingNotes()) {
            final ReportingNote reportingNote = reportingNoteRepository.findByIdAndApprovedIsNotNull(reportingNoteDto.id())
                    .orElseThrow(() -> new DecreeStateException("Approved reporting note { id = " + reportingNoteDto.id() + " } doesn't exist"));
            final Set<StudentViolation> violations = reportingNote.getViolations();
            final Map<UUID, String> dtoViolations = reportingNoteDto.violationResults();
            if (violations.size() != dtoViolations.size()) {
                throw new InvalidDecreeDataException("Reporting note { id = " + reportingNoteDto.id() + " } has wrong number of violations");
            }
            for (StudentViolation violation : violations) {
                final String result = dtoViolations.get(violation.getId());
                if (result == null) {
                    throw new InvalidDecreeDataException("Reporting note { id = " + reportingNoteDto.id() + " } has no result for violation { id = " + violation.getId() + " }");
                }
                final DecreeResult decreeResult = decreeResultRepository.findBySimpleNaturalId(result)
                        .orElseThrow(() -> new DecreeStateException("Decree result { name = " + result + " } doesn't exist"));
                violation.setDecreeResult(decreeResult);
            }
            decree.addReportingNote(reportingNote);
        }
        final Decree saved = decreeRepository.save(decree);
        log.info("New decree { id = " + saved.getId() + ", number = " + saved.getNumber()
                + ", date = " + saved.getDate() + ", creator = " + saved.getCreatedByFullName() + "} created");
        return saved.getId();
    }
}
