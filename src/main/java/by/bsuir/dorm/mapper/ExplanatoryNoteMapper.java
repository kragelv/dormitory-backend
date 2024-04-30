package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.ExplanatoryDto;
import by.bsuir.dorm.dto.request.ExplanatoryRequestDto;
import by.bsuir.dorm.model.entity.ExplanatoryNote;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ExplanatoryNoteMapper {
    ExplanatoryNote toEntity(ExplanatoryRequestDto explanatoryDto);

    ExplanatoryDto toDto(ExplanatoryNote explanatoryNote);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ExplanatoryNote partialUpdate(ExplanatoryRequestDto explanatoryDto, @MappingTarget ExplanatoryNote explanatoryNote);
}