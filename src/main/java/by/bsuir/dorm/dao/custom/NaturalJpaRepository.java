package by.bsuir.dorm.dao.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Map;
import java.util.Optional;

@NoRepositoryBean
public interface NaturalJpaRepository<T, ID, NID> extends JpaRepository<T, ID> {
    Optional<T> findBySimpleNaturalId(NID naturalId);

    Optional<T> findByNaturalId(Map<String, ?> naturalId);

    T getReferenceBySimpleNaturalId(NID naturalId);

    T getReferenceByNaturalId(Map<String, ?> naturalId);
}
