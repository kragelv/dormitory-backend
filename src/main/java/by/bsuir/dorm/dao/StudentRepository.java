package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByCardId(String cardId);

    boolean existsByCardId(String cardId);
}