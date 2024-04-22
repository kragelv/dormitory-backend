package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.LeisureDto;
import by.bsuir.dorm.dto.request.LeisureRequestDto;
import by.bsuir.dorm.model.entity.Leisure;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExtensionsMapper.class})
public interface LeisureMapper {
    Leisure toEntity(LeisureRequestDto dto);

    @Mapping(target = "studentTotalElements", source = "students", qualifiedByName = "collectionToSize")
    LeisureDto toDto(Leisure leisure);
}
