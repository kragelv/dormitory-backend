package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dao.StudentRoleRepository;
import by.bsuir.dorm.dto.StudentDto;
import by.bsuir.dorm.dto.request.RegisterStudentRequestDto;
import by.bsuir.dorm.entity.StudentRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", uses = {StudentRoleMapper.class})
public abstract class StudentMapper {

    @Autowired
    protected StudentRoleRepository studentRoleRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordNeedReset", ignore = true)
    @Mapping(target = "email", constant = "null")
    @Mapping(target = "emailConfirmed", constant = "false")
    @Mapping(target = "studentRoles", source = "roles", qualifiedByName = "mapRoleNamesToRoles")
    @Mapping(target = "fullName.surname", source = "surname")
    @Mapping(target = "fullName.name", source = "name")
    @Mapping(target = "fullName.patronymic", source = "patronymic")
    @Mapping(target = "fullNameBy.surname", source = "surnameBy")
    @Mapping(target = "fullNameBy.name", source = "nameBy")
    @Mapping(target = "fullNameBy.patronymic", source = "patronymicBy")
    public abstract by.bsuir.dorm.entity.Student toEntity(RegisterStudentRequestDto studentInput);

    @Named("mapRoleNamesToRoles")
    protected Set<StudentRole> mapRoleNamesToRoles(Collection<String> roles) { //TODO: move map logic to role mapper
        Stream<String> studentRolesStream = Stream.of("STUDENT");
        if (roles != null) {
            studentRolesStream = Stream
                    .concat(
                            studentRolesStream,
                            roles.stream()
                    );
        }
        return studentRolesStream
                .map(role -> studentRoleRepository.findByNameIgnoreCase(role)
                        .orElseThrow(() -> new IllegalArgumentException("Role '" + role + "' doesn't exist")))
                .collect(Collectors.toSet());
    }

    @Mapping(target = "roles", source = "studentRoles")
    public abstract StudentDto toDto(by.bsuir.dorm.entity.Student student);
}
