package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.ReportingNoteDto;
import by.bsuir.dorm.dto.request.ReportingNoteCreateRequestDto;
import by.bsuir.dorm.model.entity.StudentViolation;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {ViolationExtensions.class}
)
public interface ViolationMapper {
    @ValueMapping(target = "decreeResult", source = MappingConstants.NULL)
    @Mapping(target = "student", source = "studentId", qualifiedByName = "mapStudent")
    @Mapping(target = "internalRegulation", source = "regulationId", qualifiedByName = "mapInternalRegulation")
    StudentViolation toEntity(ReportingNoteCreateRequestDto.StudentViolationDto studentViolationDto);

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentFullName", source = "student.fullName")
    ReportingNoteDto.StudentViolationDto toDto(StudentViolation studentViolation);
}
