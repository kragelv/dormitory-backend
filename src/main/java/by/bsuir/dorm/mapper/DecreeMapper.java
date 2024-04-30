package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.DecreeDto;
import by.bsuir.dorm.model.entity.Decree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DecreeMapper {

    DecreeDto toDto(Decree decree);
}
