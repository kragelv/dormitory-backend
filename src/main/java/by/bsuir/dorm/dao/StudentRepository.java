package by.bsuir.dorm.dao;

import by.bsuir.dorm.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}