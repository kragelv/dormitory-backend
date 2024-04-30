package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.ReportingNoteDto;
import by.bsuir.dorm.dto.ViolationDto;
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
    @Mapping(target = "explanatoryNoteUpdated", source = "explanatoryNote.updated")
    ReportingNoteDto.StudentViolationDto toReportingNoteViolationDto(StudentViolation studentViolation);

    @Mapping(target = "decreeResult", source = "decreeResult.name")
    @Mapping(target = "reportingNoteId", source = "reportingNote.id")
    @Mapping(target = "reportingNoteApproved", source = "reportingNote.approved")
    @Mapping(target = "creator", source = "reportingNote.caretaker")
    @Mapping(target = "explanatoryNoteUpdated", source = "explanatoryNote.updated")
    ViolationDto toDto(StudentViolation studentViolation);
}
