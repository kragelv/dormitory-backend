package by.bsuir.dorm.dao;

import by.bsuir.dorm.dao.custom.NaturalJpaRepository;
import by.bsuir.dorm.model.entity.InternalRegulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InternalRegulationRepository extends NaturalJpaRepository<InternalRegulation, UUID, String> {
    @Query("""
            select i from InternalRegulation i
            where upper(concat( i.itemString, ' ', i.content)) like upper(concat('%', ?1, '%')) escape '\\'""")
    Page<InternalRegulation> findAllBySearchString(String search, Pageable pageable);
}