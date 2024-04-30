package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.ExplanatoryNoteRepository;
import by.bsuir.dorm.dao.StudentViolationRepository;
import by.bsuir.dorm.dto.ExplanatoryDto;
import by.bsuir.dorm.dto.request.ExplanatoryRequestDto;
import by.bsuir.dorm.exception.ExplanatoryNotFoundException;
import by.bsuir.dorm.exception.ExplanatoryNoteAlreadyExistsException;
import by.bsuir.dorm.exception.ViolationNotFoundException;
import by.bsuir.dorm.mapper.ExplanatoryNoteMapper;
import by.bsuir.dorm.model.entity.*;
import by.bsuir.dorm.service.ExplanatoryService;
import by.bsuir.dorm.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ExplanatoryServiceImpl implements ExplanatoryService {
    private final ExplanatoryNoteRepository explanatoryNoteRepository;
    private final UserSecurityService userSecurityService;
    private final StudentViolationRepository studentViolationRepository;
    private final ExplanatoryNoteMapper explanatoryNoteMapper;

    @Override
    public ExplanatoryDto create(String username, UUID id, ExplanatoryRequestDto dto) {
        final Student student = userSecurityService.findStudentByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student { id = " + username + " } not found"));
        final StudentViolation studentViolation = studentViolationRepository.findById(id)
                .orElseThrow(() -> new ViolationNotFoundException("Violation { id = " + id + " } doesn't exist"));
        if (studentViolation.getStudent().getId() != student.getId()) {
            throw new AccessDeniedException("Violation { id = " + id + " } doesn't belong to student { id = " + student.getId() + " }");
        }
        if (studentViolation.getExplanatoryNote() != null) {
            throw new ExplanatoryNoteAlreadyExistsException("Violation { id = " + id + " } already has explanatory note");
        }
        final ExplanatoryNote explanatoryNote = explanatoryNoteMapper.toEntity(dto);
        explanatoryNote.setUpdated(Instant.now());
        final ExplanatoryNote saved = explanatoryNoteRepository.save(explanatoryNote);
        studentViolation.setExplanatoryNote(saved);
        log.info("Create explanatory note { violation = " + studentViolation.getId() + ", id = " + saved.getId() + " }");
        return explanatoryNoteMapper.toDto(saved);
    }

    @Override
    public ExplanatoryDto update(String username, UUID id, ExplanatoryRequestDto dto) {
        final Student student = userSecurityService.findStudentByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student { id = " + username + " } not found"));
        final StudentViolation studentViolation = studentViolationRepository.findById(id)
                .orElseThrow(() -> new ViolationNotFoundException("Violation { id = " + id + " } doesn't exist"));
        if (studentViolation.getStudent().getId() != student.getId()) {
            throw new AccessDeniedException("Violation { id = " + id + " } doesn't belong to student { id = " + student.getId() + " }");
        }
        final ExplanatoryNote explanatoryNote = studentViolation.getExplanatoryNote();
        if (explanatoryNote == null) {
            throw new ExplanatoryNotFoundException("Explanatory note { violation = " + studentViolation.getId() + " } doesn't exist");
        }
        final ExplanatoryNote updated = explanatoryNoteMapper.partialUpdate(dto, explanatoryNote);
        log.info("Update explanatory note { violation = " + studentViolation.getId() + ", id = " + updated.getId() + " }");
        return explanatoryNoteMapper.toDto(updated);
    }

    @Override
    public ExplanatoryDto get(String username, UUID id) {
        final User user = userSecurityService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User { id = " + username + " } not found"));
        final StudentViolation studentViolation = studentViolationRepository.findById(id)
                .orElseThrow(() -> new ViolationNotFoundException("Violation { id = " + id + " } doesn't exist"));
        if (user instanceof Student student) {
            if (studentViolation.getStudent().getId() != student.getId()) {
                throw new AccessDeniedException("Violation { id = " + id + " } doesn't belong to student { id = " + student.getId() + " }");
            }
            return explanatoryNoteMapper.toDto(studentViolation.getExplanatoryNote());
        } else if (user instanceof Employee) {
            return explanatoryNoteMapper.toDto(studentViolation.getExplanatoryNote());
        } else {
            throw new UsernameNotFoundException("Invalid user { id = " + username + " }");
        }
    }
}
