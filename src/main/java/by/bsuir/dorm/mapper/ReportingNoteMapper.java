package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.ReportingNoteDto;
import by.bsuir.dorm.dto.request.ReportingNoteCreateRequestDto;
import by.bsuir.dorm.model.entity.ReportingNote;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ViolationMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface ReportingNoteMapper {
    @Mapping(target = "approved", expression = "java(null)")
    @Mapping(target = "decree", expression = "java(null)")
    @Mapping(target = "caretaker", ignore = true)
    ReportingNote toEntity(ReportingNoteCreateRequestDto reportingNoteDto);

    ReportingNoteDto toDto(ReportingNote reportingNote);
}
