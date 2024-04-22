package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.ContractRepository;
import by.bsuir.dorm.dao.StudentRepository;
import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dto.request.RegisterStudentRequestDto;
import by.bsuir.dorm.exception.ContractNotFoundException;
import by.bsuir.dorm.exception.StudentAlreadyExistsException;
import by.bsuir.dorm.exception.StudentAlreadyHasContractException;
import by.bsuir.dorm.exception.UserNotFoundException;
import by.bsuir.dorm.mapper.StudentMapper;
import by.bsuir.dorm.model.entity.Contract;
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
    private final StudentRepository studentRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID register(RegisterStudentRequestDto dto) {
        if (studentRepository.existsByCardId(dto.cardId())) {
            throw new StudentAlreadyExistsException("Student { cardId = } already exists");
        }
        final Contract contract = contractRepository.findByIdAndActiveAndStudentNull(dto.contractId())
                .orElseThrow(() -> new ContractNotFoundException("Unregistered contract { id = " +
                        dto.contractId() + " } doesn't exist"));
        final Student student = studentMapper.toEntity(dto);
        student.setPasswordNeedReset(true);
        student.setPassword(passwordEncoder.encode(dto.cardId()));
        contract.setStudent(student);
        final Student saved = userRepository.save(student);
        log.info("Register new Student: { id = " + saved.getId() +
                ", cardId = " + saved.getCardId() + "; fullName = " + saved.getFullName());
        return saved.getId();
    }

    @Override
    public UUID update(RegisterStudentRequestDto dto) {
        final String cardId = dto.cardId();
        final Student student = studentRepository.findByCardId(cardId)
                .orElseThrow((() -> new UserNotFoundException("Student { cardId = " + cardId + " } doesn't exist")));
        final Contract contract = contractRepository.findByIdAndActiveAndStudentNull(dto.contractId())
                .orElseThrow(() -> new ContractNotFoundException("Unregistered contract { id = " +
                        dto.contractId() + " } doesn't exist"));
        contractRepository.findByStudentAndActive(student).ifPresent((activeContract) -> {
            throw new StudentAlreadyHasContractException("Student has contract { id = "
                    + activeContract.getId() + " }");
        });
        studentMapper.partialUpdate(dto, student);
        contract.setStudent(student);
        final Student saved = userRepository.save(student);
        log.info("Register (update) existing Student: { id = " + saved.getId() +
                ", cardId = " + saved.getCardId() + "; fullName = " + saved.getFullName());
        return saved.getId();
    }
}
