package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.Employee;
import by.bsuir.dorm.model.entity.Leisure;
import by.bsuir.dorm.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LeisureRepository extends JpaRepository<Leisure, UUID> {
    Page<Leisure> findByOrganizer(Employee organizer, Pageable pageable);

    Page<Leisure> findByStudentsContains(Student student, Pageable pageable);

    Page<Leisure> findByOrganizerAndStudentsContains(Employee organizer, Student student, Pageable pageable);

    boolean existsByIdAndStudentsContains(UUID id, Student student);
}