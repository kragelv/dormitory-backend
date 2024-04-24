package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.InternalRegulationDto;
import by.bsuir.dorm.dto.request.InternalRegulationRequestDto;
import by.bsuir.dorm.model.entity.InternalRegulation;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ExtensionsMapper.class})
public interface RegulationMapper {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemStringToItems")
    @Mapping(target = "itemString", source = "item")
    InternalRegulation toEntity(InternalRegulationRequestDto internalRegulationDto);

    @Mapping(target = "item", source = "itemString")
    InternalRegulationDto toDto(InternalRegulation internalRegulation);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InternalRegulation partialUpdate(InternalRegulationRequestDto internalRegulationDto,
                                     @MappingTarget InternalRegulation internalRegulation);
}
