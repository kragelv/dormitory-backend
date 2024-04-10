package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.dto.userpersonal.PersonalUserDto;
import by.bsuir.dorm.dto.userpublic.PublicUserDto;
import by.bsuir.dorm.exception.UserNotFoundException;
import by.bsuir.dorm.mapper.UserMapper;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserSecurityServiceImpl userSecurityService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public PersonalUserDto getFullInfo(String username) {
        User user = userSecurityService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User { id = " + id  +" } is not found"));
        return userMapper.toPersonalDto(user);
    }

    @Override
    public PublicUserDto getPublicInfoById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User { id = " + id + " } doesn't exist"));
        return userMapper.toPublicDto(user);
    }
}
