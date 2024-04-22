package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.EmployeeRepository;
import by.bsuir.dorm.dao.StudentRepository;
import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.model.entity.Employee;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        final UUID id = valueOf(username);
        return userRepository.findById(id);
    }

    @Override
    public Optional<Employee> findEmployeeByUsername(String username) {
        final UUID id = valueOf(username);
        return employeeRepository.findById(id);
    }

    @Override
    public Optional<Student> findStudentByUsername(String username) {
        final UUID id = valueOf(username);
        return studentRepository.findById(id);
    }

    private static UUID valueOf(String username) {
        final UUID id;
        try {
            id = UUID.fromString(username);
        } catch (IllegalArgumentException ex) {
            throw new UsernameNotFoundException("Invalid user id: " + username);
        }
        return id;
    }
}
