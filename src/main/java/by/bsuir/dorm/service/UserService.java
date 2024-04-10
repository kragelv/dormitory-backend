package by.bsuir.dorm.service;

import by.bsuir.dorm.dto.userpersonal.PersonalUserDto;
import by.bsuir.dorm.dto.userpublic.PublicUserDto;

import java.util.UUID;

public interface UserService {
    PersonalUserDto getFullInfo(String username);

    PublicUserDto getPublicInfoById(UUID id);
}
