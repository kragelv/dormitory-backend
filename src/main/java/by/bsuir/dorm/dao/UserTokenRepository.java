package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.TokenPurpose;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.model.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {
    Optional<UserToken> findByPurposeAndId(TokenPurpose purpose, UUID id);

    void deleteAllByPurposeAndUser(TokenPurpose purpose, User user);

}
