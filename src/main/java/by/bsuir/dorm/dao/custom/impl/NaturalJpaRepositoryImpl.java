package by.bsuir.dorm.dao.custom.impl;

import by.bsuir.dorm.dao.custom.NaturalJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@Transactional
public class NaturalJpaRepositoryImpl<T, ID extends Serializable, NID>
        extends SimpleJpaRepository<T, ID>
        implements NaturalJpaRepository<T, ID, NID> {
    @PersistenceContext
    private final EntityManager entityManager;

    public NaturalJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                    EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findBySimpleNaturalId(NID naturalId) {
        return entityManager.unwrap(Session.class)
                .bySimpleNaturalId(this.getDomainClass())
                .loadOptional(naturalId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findByNaturalId(Map<String, ?> naturalIds) {
        return entityManager.unwrap(Session.class)
                .byNaturalId(this.getDomainClass())
                .using(naturalIds)
                .loadOptional();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> getReferenceBySimpleNaturalId(NID naturalId) {
        return Optional.ofNullable(
                entityManager.unwrap(Session.class)
                        .bySimpleNaturalId(this.getDomainClass())
                        .getReference(naturalId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> getReferenceByNaturalId(Map<String, ?> naturalId) {
        return Optional.ofNullable(
                entityManager.unwrap(Session.class)
                .byNaturalId(this.getDomainClass())
                .using(naturalId)
                .getReference()
        );
    }
}
