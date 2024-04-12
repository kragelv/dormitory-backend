package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dto.request.RegisterEmployeeRequestDto;
import by.bsuir.dorm.mapper.EmployeeMapper;
import by.bsuir.dorm.model.entity.Employee;
import by.bsuir.dorm.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID register(RegisterEmployeeRequestDto dto){
        final Employee employee = employeeMapper.toEntity(dto);
        final String passwordToEncode;
        if (dto.password() == null) {
            passwordToEncode = dto.cardId();;
            employee.setPasswordNeedReset(true);
        } else {
            passwordToEncode = dto.password();
            employee.setPasswordNeedReset(false);
        }
        employee.setPassword(passwordEncoder.encode(passwordToEncode));
        final Employee saved = userRepository.save(employee);
        log.info("Register new Employee: { id = " + saved.getId() +
                "; cardId = " + saved.getCardId() + "; fullName = " + saved.getFullName());
        return saved.getId();
    }
}
