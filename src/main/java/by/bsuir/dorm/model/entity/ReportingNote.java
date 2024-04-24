package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_reporting_note")
public class ReportingNote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "caretaker_id", nullable = false)
    private Employee caretaker;

    @Column(name = "r_date", nullable = false)
    private LocalDate date;

    @Column(name = "approved_date")
    private Instant approved;

    @OneToMany(mappedBy = "reportingNote", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private Set<StudentViolation> violations = new LinkedHashSet<>();

    public void addViolation(StudentViolation violation) {
        violations.add(violation);
        violation.setReportingNote(this);
    }

    public void removeViolation(StudentViolation violation) {
        violations.remove(violation);
        violation.setReportingNote(null);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "decree_id")
    private Decree decree;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportingNote other = (ReportingNote) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}