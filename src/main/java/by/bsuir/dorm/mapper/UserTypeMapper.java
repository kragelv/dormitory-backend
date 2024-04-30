package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.UserTypeDto;
import by.bsuir.dorm.model.entity.UserType;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {

    UserType toEntity(String name);

    UserType toEntity(UserTypeDto userTypeDto);

    List<UserType> toEntity(Collection<UserTypeDto> userTypeDto);

    UserTypeDto toDto(UserType userType);

    List<UserTypeDto> toDto(Collection<UserType> userType);
}
