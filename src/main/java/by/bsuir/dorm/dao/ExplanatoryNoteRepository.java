package by.bsuir.dorm.dao;

import by.bsuir.dorm.model.entity.ExplanatoryNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExplanatoryNoteRepository extends JpaRepository<ExplanatoryNote, UUID> {
}