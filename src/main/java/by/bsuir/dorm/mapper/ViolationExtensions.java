package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dao.InternalRegulationRepository;
import by.bsuir.dorm.dao.StudentRepository;
import by.bsuir.dorm.dto.request.ReportingNoteCreateRequestDto;
import by.bsuir.dorm.exception.RegulationNotFoundException;
import by.bsuir.dorm.exception.UserNotFoundException;
import by.bsuir.dorm.model.entity.InternalRegulation;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.model.entity.StudentViolation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ViolationExtensions {
    private final StudentRepository studentRepository;
    private final InternalRegulationRepository internalRegulationRepository;
    @Named("mapStudent")
    public Student mapStudent(UUID studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("Student { id = '" + studentId + "' } doesn't exist"));
    }

    @Named("mapInternalRegulation")
    public InternalRegulation mapInternalRegulation(UUID regulationId) {
        return internalRegulationRepository.findById(regulationId)
                .orElseThrow(() -> new RegulationNotFoundException("InternalRegulation { id = '" + regulationId + "' } doesn't exist"));
    }
}
