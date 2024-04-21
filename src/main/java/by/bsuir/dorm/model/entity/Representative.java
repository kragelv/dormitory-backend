package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_representative")
public class Representative {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "surname", column = @Column(name = "surname", length = 40, nullable = false)),
            @AttributeOverride(name = "name", column = @Column(name = "name", length = 40, nullable = false))
    })
    private FullName fullName;

    @Embedded
    private Passport passport;

    @OneToOne(mappedBy = "representative", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false, orphanRemoval = true)
    private Contract contract;

}