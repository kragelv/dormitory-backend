package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.RefreshToken;
import by.bsuir.dorm.model.entity.RefreshTokenKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, RefreshTokenKey> {

}
