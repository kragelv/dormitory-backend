package by.bsuir.dorm.dao;

import by.bsuir.dorm.dao.custom.NaturalJpaRepository;
import by.bsuir.dorm.model.entity.Role;
import by.bsuir.dorm.model.entity.UserType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends NaturalJpaRepository<Role, Integer, String> {
    List<Role> findAllByCompatibleUserTypesContainsOrderById(UserType userType);
}
