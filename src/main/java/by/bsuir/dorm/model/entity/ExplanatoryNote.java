package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_explanatory_note")
public class ExplanatoryNote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "recipient", nullable = false, length = 255)
    private String recipient;

    @Column(name = "content", nullable = false, length = 512)
    private String content;

    @UpdateTimestamp
    private Instant updated;

    @OneToOne(mappedBy = "explanatoryNote", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    private StudentViolation studentViolation;

    public void addStudentViolation(StudentViolation studentViolation) {
        studentViolation.setExplanatoryNote(this);
        this.studentViolation = studentViolation;
    }

    public void removeStudentViolation() {
        if (studentViolation != null) {
            studentViolation.setExplanatoryNote(null);
            studentViolation = null;
        }
    }
}