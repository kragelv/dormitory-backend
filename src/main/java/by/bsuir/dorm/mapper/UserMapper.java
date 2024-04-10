package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.userpersonal.PersonalEmployeeDto;
import by.bsuir.dorm.dto.userpersonal.PersonalStudentDto;
import by.bsuir.dorm.dto.userpersonal.PersonalUserDto;
import by.bsuir.dorm.dto.userpublic.PublicEmployeeDto;
import by.bsuir.dorm.dto.userpublic.PublicStudentDto;
import by.bsuir.dorm.dto.userpublic.PublicUserDto;
import by.bsuir.dorm.model.entity.Employee;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(
        componentModel = "spring",
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION,
        uses = {StudentMapper.class, ExtensionsMapper.class}
)
public interface UserMapper {
    @SubclassMapping(source = Student.class, target = PersonalStudentDto.class)
    @SubclassMapping(source = Employee.class, target = PersonalEmployeeDto.class)
    PersonalUserDto toPersonalDto(User user);

    @SubclassMapping(source = Student.class, target = PublicStudentDto.class)
    @SubclassMapping(source = Employee.class, target = PublicEmployeeDto.class)
    PublicUserDto toPublicDto(User user);
}
