package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.RefreshToken;
import by.bsuir.dorm.model.entity.RefreshTokenKey;
import by.bsuir.dorm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, RefreshTokenKey> {

    long deleteByRefreshTokenKeyUser(User user);
}
