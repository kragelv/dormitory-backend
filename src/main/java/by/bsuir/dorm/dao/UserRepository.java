package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByCardId(String cardId);

    Optional<User> findByEmailIgnoreCase(String email);
}