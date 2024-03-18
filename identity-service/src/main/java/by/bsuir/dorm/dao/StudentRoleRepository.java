package by.bsuir.dorm.dao;

import by.bsuir.dorm.entity.StudentRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRoleRepository extends JpaRepository<StudentRole, Integer> {
    Optional<StudentRole> findByNameIgnoreCase(String name);
}
