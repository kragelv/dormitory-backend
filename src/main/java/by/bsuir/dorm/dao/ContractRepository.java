package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.Contract;
import by.bsuir.dorm.model.entity.Room;
import by.bsuir.dorm.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {
    @Query("""
            select c from Contract c
            where c.id = ?1 and c.student is null and c.terminationDate is null and current_date between c.startDate and c.expiryDate""")
    Optional<Contract> findByIdAndActiveAndStudentNull(UUID id);

    @Query("""
            select c from Contract c
            where c.student = ?1 and c.terminationDate is null and current_date between c.startDate and c.expiryDate""")
    Optional<Contract> findByStudentAndActive(Student student);

    @Query("""
            select c from Contract c
            where c.id = ?1 and c.terminationDate is null and current_date between c.startDate and c.expiryDate""")
    Optional<Contract> findByIdActive(UUID id);

    @Query("""
            select c from Contract c
            where c.terminationDate is null and current_date between c.startDate and c.expiryDate""")
    Page<Contract> findAllActive(Pageable pageable);

    @Query("""
            select c from Contract c
            where c.student is null and c.terminationDate is null and current_date between c.startDate and c.expiryDate""")
    Page<Contract> findAllActiveAndStudentNull(Pageable pageable);

    @Query("""
            select c from Contract c
            where c.room = ?1 and c.terminationDate is null and current_date between c.startDate and c.expiryDate""")
    List<Contract> findAllActiveByRoom(Room room);
}