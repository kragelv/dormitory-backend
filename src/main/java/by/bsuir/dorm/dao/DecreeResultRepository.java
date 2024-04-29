package by.bsuir.dorm.dao;

import by.bsuir.dorm.dao.custom.NaturalJpaRepository;
import by.bsuir.dorm.model.entity.DecreeResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DecreeResultRepository extends NaturalJpaRepository<DecreeResult, UUID, String> {
}