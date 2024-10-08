package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_room")
@NaturalIdCache
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NaturalId(mutable = true)
    @PositiveOrZero
    @Column(name = "number", nullable = false, unique = true)
    private Integer number;

    @Positive
    @Column(name = "floor", nullable = false)
    private Integer floor;

    @PositiveOrZero
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Contract> contracts = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        contracts.forEach(contract -> contract.setRoom(null));
    }
}