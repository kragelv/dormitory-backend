package by.bsuir.dorm.dao;

import by.bsuir.dorm.dao.custom.NaturalJpaRepository;
import by.bsuir.dorm.model.entity.UserType;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends NaturalJpaRepository<UserType, Integer, String> {

}
