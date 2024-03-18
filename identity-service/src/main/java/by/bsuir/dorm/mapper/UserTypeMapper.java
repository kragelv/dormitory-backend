package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dto.UserTypeDto;
import by.bsuir.dorm.entity.UserType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    default UserTypeDto toDto(UserType userType) {
        return new UserTypeDto(userType.getIntValue(), userType.name());
    };
}
