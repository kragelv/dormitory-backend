package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.GroupDto;
import by.bsuir.dorm.dto.request.GroupRequestDto;
import by.bsuir.dorm.model.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ExtensionsMapper.class}
)
public interface GroupMapper {

    Group toEntity(GroupRequestDto groupDto);

    @Mapping(target = "studentTotalElements", source = "students", qualifiedByName = "collectionToSize")
    GroupDto toDto(Group group);
}