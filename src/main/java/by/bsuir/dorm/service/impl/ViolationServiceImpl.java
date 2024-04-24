package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.StudentViolationRepository;
import by.bsuir.dorm.dto.ViolationDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.ViolationNotFoundException;
import by.bsuir.dorm.mapper.ViolationMapper;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.model.entity.StudentViolation;
import by.bsuir.dorm.service.UserSecurityService;
import by.bsuir.dorm.service.ViolationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ViolationServiceImpl implements ViolationService {
    private final UserSecurityService userSecurityService;
    private final StudentViolationRepository studentViolationRepository;
    private final ViolationMapper violationMapper;

    @Override
    public PageResponse<ViolationDto> getAllMy(String username, int page, int limit) {
        final Student student = userSecurityService.findStudentByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student { id = " + username + " } not found"));
        final Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "date"));
        final Page<StudentViolation> pageResult = studentViolationRepository.findAllByStudent(student, pageable);
        return PageResponse.create(pageResult, violationMapper::toDto);
    }

    @Override
    public ViolationDto getById(String username, UUID id) {
        final Student student = userSecurityService.findStudentByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student { id = " + username + " } not found"));
        final StudentViolation studentViolation = studentViolationRepository.findById(id)
                .orElseThrow(() -> new ViolationNotFoundException("Violation { id = " + id + " } doesn't exist"));
        if (studentViolation.getStudent().getId() != student.getId()) {
            throw new AccessDeniedException("Violation { id = " + id + " } doesn't belong to student { id = " + student.getId() + " }");
        }
        return violationMapper.toDto(studentViolation);
    }
}
