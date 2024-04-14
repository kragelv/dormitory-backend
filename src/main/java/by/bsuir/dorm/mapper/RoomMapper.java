package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.model.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room toEntity(RoomDto roomDto);

    RoomDto toDto(Room room);
}
