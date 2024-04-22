package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.request.RoomRequestDto;
import by.bsuir.dorm.model.entity.Room;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {RoomExtensions.class})
public interface RoomMapper {
    Room toEntity(RoomRequestDto dto);

    @Mapping(target = "current", source = ".", qualifiedByName = "getCurrentNumber")
    RoomDto toDto(Room room);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Room partialUpdate(RoomRequestDto roomRequestDto, @MappingTarget Room room);
}