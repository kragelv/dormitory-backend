package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.Contract;
import by.bsuir.dorm.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
    Optional<Contract> findByIdAndStudentNull(UUID id);

    @Query("""
            select c from Contract c
            where c.student = ?1 and c.terminationDate is null and current_date between c.startDate and c.expiryDate""")
    Optional<Contract> findActiveContractByStudent(Student student);

    @Query("""
            select c from Contract c
            where c.id = ?1 and c.terminationDate is null and current_date between c.startDate and c.expiryDate""")
    Optional<Contract> findActiveContractById(UUID id);
}