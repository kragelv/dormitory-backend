package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_decree")
public class Decree {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "number", nullable = false, updatable = false)
    private Integer number;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "created_by_id")
    private Employee createdBy;

    @Column(name = "created_by_full_name")
    private String createdByFullName;

    @OneToMany(mappedBy = "decree", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private Set<ReportingNote> reportingNotes = new LinkedHashSet<>();

    public void addReportingNote(ReportingNote reportingNote) {
        reportingNotes.add(reportingNote);
        reportingNote.setDecree(this);
    }

    public void removeReportingNote(ReportingNote reportingNote) {
        reportingNotes.remove(reportingNote);
        reportingNote.setDecree(null);
    }
}