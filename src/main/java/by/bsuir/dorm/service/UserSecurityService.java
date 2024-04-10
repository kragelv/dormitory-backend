package by.bsuir.dorm.service;

import by.bsuir.dorm.model.entity.User;

import java.util.Optional;

public interface UserSecurityService {
    Optional<User> findByUsername(String id);

}
