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
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "card_id", length = 64)
    private String cardId;

    @Column(name = "number", nullable = false, updatable = false)
    private Integer number;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name = "expiry_date", nullable = false, updatable = false)
    private LocalDate expiryDate;

    @Column(name = "termination_date")
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
    private Passport passport;

    @Column(name = "phone_number", nullable = false, length = 16)
    private String phoneNumber;

    @Column(name = "residential_address", nullable = false, length = 255)
    private String residentialAddress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "representative_id")
    private Representative representative;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Column(name = "created_by_full_name")
    private String createdByFullName;

}