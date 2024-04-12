package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "tbl_contract",
        indexes = {
                @Index(
                        name = "idx_termination_date",
                        columnList = "termination_date"
                )
        }
)

public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false
    )
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false
    )
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "card_id", length = 64)
    private String cardId;

    @Column(name = "number", nullable = false, unique = true, updatable = false)
    private Integer number;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "termination_date", updatable = false)
    private LocalDate terminationDate;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "surname", column = @Column(name = "surname", length = 40, nullable = false)),
            @AttributeOverride(name = "name", column = @Column(name = "name", length = 40, nullable = false))
    })
    private FullName fullName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "series", column = @Column(name = "passport_series", nullable = false, length = 2)),
            @AttributeOverride(name = "number", column = @Column(name = "passport_number", nullable = false, length = 7)),
            @AttributeOverride(name = "issueDate", column = @Column(name = "passport_issue_date", nullable = false)),
            @AttributeOverride(name = "issuePlace", column = @Column(name = "passport_issue_place", nullable = false))
    })
    private Passport passport;

    @Column(name = "residential_address", nullable = false, length = 255)
    private String residentialAddress;

    @Column(name = "birthplace", nullable = false, length = 255)
    private String birthplace;
}