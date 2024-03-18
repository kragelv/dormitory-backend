package by.bsuir.dorm.dao;

import by.bsuir.dorm.entity.RefreshToken;
import by.bsuir.dorm.entity.RefreshTokenKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, RefreshTokenKey> {

}
