package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.Decree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DecreeRepository extends JpaRepository<Decree, UUID> {

    Page<Decree> findAllByCreatedBy(UUID createdBy, Pageable pageable);
}