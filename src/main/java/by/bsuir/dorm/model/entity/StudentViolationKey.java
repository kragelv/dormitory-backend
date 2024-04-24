package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class StudentViolationKey implements Serializable {
    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "reporting_note_id", nullable = false)
    private UUID reportingNoteId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentViolationKey other = (StudentViolationKey) o;
        return Objects.equals(studentId, other.studentId) && Objects.equals(reportingNoteId, other.reportingNoteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, reportingNoteId);
    }
}
