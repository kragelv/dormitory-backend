package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.model.entity.StudentViolation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentViolationRepository extends JpaRepository<StudentViolation, UUID> {
    Page<StudentViolation> findAllByStudent(Student student, Pageable pageable);
}