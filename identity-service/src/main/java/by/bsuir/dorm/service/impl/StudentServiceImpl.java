package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dto.request.RegisterStudentRequestDto;
import by.bsuir.dorm.entity.Student;
import by.bsuir.dorm.mapper.StudentMapper;
import by.bsuir.dorm.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID register(RegisterStudentRequestDto dto){
        Student student = studentMapper.toEntity(dto);
        final String passwordToEncode;
        if (dto.password() == null) {
            passwordToEncode = dto.cardId();;
            student.setPasswordNeedReset(true);
        } else {
            passwordToEncode = dto.password();
            student.setPasswordNeedReset(false);
        }
        student.setPassword(passwordEncoder.encode(passwordToEncode));
        Student saved = userRepository.save(student);
        return saved.getId();
    }
}
