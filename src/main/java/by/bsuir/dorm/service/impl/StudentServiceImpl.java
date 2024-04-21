package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dto.request.RegisterStudentRequestDto;
import by.bsuir.dorm.mapper.StudentMapper;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID register(RegisterStudentRequestDto dto) {
        final Student student = studentMapper.toEntity(dto);
        student.setPasswordNeedReset(true);
        student.setPassword(passwordEncoder.encode(dto.cardId()));
        final Student saved = userRepository.save(student);
        log.info("Register new Student: { id = " + saved.getId() +
                ", cardId = " + saved.getCardId() + "; fullName = " + saved.getFullName());
        return saved.getId();
    }
}
