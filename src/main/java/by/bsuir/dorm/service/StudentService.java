package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.request.RegisterStudentRequestDto;

import java.util.UUID;

public interface StudentService {
    UUID register(RegisterStudentRequestDto dto);

    UUID update(RegisterStudentRequestDto dto);
}
