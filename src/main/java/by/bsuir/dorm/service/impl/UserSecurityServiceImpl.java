package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        final UUID id;
        try {
            id = UUID.fromString(username);
        } catch (IllegalArgumentException ex) {
            throw new UsernameNotFoundException("Invalid user id: " + username);
        }
        return userRepository.findById(id);
    }
}
