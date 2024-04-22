package by.bsuir.dorm.service;

import by.bsuir.dorm.model.entity.Employee;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.model.entity.User;

import java.util.Optional;

public interface UserSecurityService {
    Optional<User> findByUsername(String id);

    Optional<Employee> findEmployeeByUsername(String username);

    Optional<Student> findStudentByUsername(String username);
}
