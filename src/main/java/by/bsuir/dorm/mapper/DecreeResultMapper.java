package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.DecreeResultDto;
import by.bsuir.dorm.model.entity.DecreeResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DecreeResultMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    DecreeResult toEntity(String name);

    DecreeResultDto toDto(DecreeResult decreeResult);
}
