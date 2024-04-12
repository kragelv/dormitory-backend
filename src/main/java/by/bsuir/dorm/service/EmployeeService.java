package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.request.RegisterEmployeeRequestDto;

import java.util.UUID;

public interface EmployeeService {
    UUID register(RegisterEmployeeRequestDto dto);
}
