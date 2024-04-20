package by.bsuir.dorm.dao;

import by.bsuir.dorm.dao.custom.NaturalJpaRepository;
import by.bsuir.dorm.model.entity.Group;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends NaturalJpaRepository<Group, UUID, String> {

    boolean existsByNumber(String number);
}