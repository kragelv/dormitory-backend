package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.EmployeeRepository;
import by.bsuir.dorm.dao.LeisureRepository;
import by.bsuir.dorm.dao.StudentRepository;
import by.bsuir.dorm.dto.LeisureDto;
import by.bsuir.dorm.dto.LeisureStudentDto;
import by.bsuir.dorm.dto.request.LeisureRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.LeisureNotFoundException;
import by.bsuir.dorm.exception.LeisureStateException;
import by.bsuir.dorm.mapper.LeisureMapper;
import by.bsuir.dorm.mapper.StudentMapper;
import by.bsuir.dorm.model.entity.Employee;
import by.bsuir.dorm.model.entity.Leisure;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.service.LeisureService;
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

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LeisureServiceImpl implements LeisureService {
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;
    private final LeisureRepository leisureRepository;
    private final LeisureMapper leisureMapper;
    private final UserSecurityService userSecurityService;
    private final StudentMapper studentMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<LeisureDto> getAll(int page, int limit, UUID organizerId, UUID studentId) {
        final Employee organizer;
        if (organizerId != null) {
            organizer = employeeRepository.findById(organizerId)
                    .orElseThrow(() -> new LeisureNotFoundException("Employee { id = " + organizerId + " } doesn't exist"));
        } else {
            organizer = null;
        }
        final Student student;
        if (studentId != null) {
            student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new LeisureNotFoundException("Student { id = " + studentId + " } doesn't exist"));
        } else {
            student = null;
        }
        final Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "day"));
        final Page<Leisure> pageResult;
        if (organizer == null && student == null) {
            pageResult = leisureRepository.findAll(pageable);
        } else if (organizer != null && student != null) {
            pageResult = leisureRepository.findByOrganizerAndStudentsContains(organizer, student, pageable);
        } else if (organizer != null) {
            pageResult = leisureRepository.findByOrganizer(organizer, pageable);
        } else  {
            pageResult = leisureRepository.findByStudentsContains(student, pageable);
        }
        log.info("Get leisures [page = " + page + ", limit = " + limit + ", organizer = " + organizerId +
                ", student = " + studentId + "] : totalElements = " + pageResult.getTotalElements() +
                ", numberOfElements = " + pageResult.getNumberOfElements());
        return PageResponse.create(pageResult, leisureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public LeisureDto getById(UUID id) {
        final Leisure leisure = leisureRepository.findById(id)
                .orElseThrow(() -> new LeisureNotFoundException("Leisure { id = " + id + " } doesn't exist"));
        return leisureMapper.toDto(leisure);
    }


    @Override
    public UUID create(String creator, LeisureRequestDto dto) {
        final Employee organizer = userSecurityService.findEmployeeByUsername(creator)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid creator id = " + creator));
        final Leisure leisure = leisureMapper.toEntity(dto);
        leisure.setOrganizer(organizer);
        final Leisure saved = leisureRepository.save(leisure);
        return saved.getId();
    }

    @Override
    public void deleteById(UUID id) {
        leisureRepository.findById(id).ifPresent(leisure -> {
            if (!leisure.getStudents().isEmpty()) {
                throw new LeisureStateException("Leisure { id = " + leisure.getId() + " } has students");
            }
            log.info("Delete leisure by id { id = " + leisure.getId() + " }");
            leisureRepository.delete(leisure);
        });
    }

    @Override
    public List<LeisureStudentDto> getStudentsByLeisureId(UUID id) {
        final Leisure leisure = leisureRepository.findById(id)
                .orElseThrow(() -> new LeisureNotFoundException("Leisure { id = " + id + " } doesn't exist"));
        return leisure.getStudents()
                .stream()
                .map(studentMapper::toLeisureStudentDto)
                .toList();
    }

    @Override
    public boolean isParticipant(String username, UUID leisureId) {
        final Student student = userSecurityService.findStudentByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student { id = " + username + " } doesn't exist"));
        leisureRepository.findById(leisureId)
                .orElseThrow(() -> new LeisureNotFoundException("Leisure { id = " + leisureId + " } doesn't exist"));
        return leisureRepository.existsByIdAndStudentsContains(leisureId, student);
    }

    @Override
    public void studentJoin(String username, UUID leisureId) {
        final Student student = userSecurityService.findStudentByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student { id = " + username + " } doesn't exist"));
        final Leisure leisure = leisureRepository.findById(leisureId)
                .orElseThrow(() -> new LeisureNotFoundException("Leisure { id = " + leisureId + " } doesn't exist"));
        leisure.getStudents().add(student);
        leisureRepository.save(leisure);
    }

    @Override
    public void studentLeave(String username, UUID leisureId) {
        final Student student = userSecurityService.findStudentByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student { id = " + username + " } doesn't exist"));
        final Leisure leisure = leisureRepository.findById(leisureId)
                .orElseThrow(() -> new LeisureNotFoundException("Leisure { id = " + leisureId + " } doesn't exist"));
        leisure.getStudents().remove(student);
        leisureRepository.save(leisure);
    }
}
