package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.StudentDto;
import by.bsuir.dorm.dto.UserDto;
import by.bsuir.dorm.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(
        componentModel = "spring",
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION,
        uses = {StudentMapper.class}
)
public interface UserMapper {
    @SubclassMapping(source = by.bsuir.dorm.entity.Student.class, target = StudentDto.class)
    UserDto toDto(User user);
}
