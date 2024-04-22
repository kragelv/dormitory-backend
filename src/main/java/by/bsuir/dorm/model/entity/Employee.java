package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_employee")
@PrimaryKeyJoinColumn(name = "user_id")
public class Employee extends User {
    @Column(name = "residential_address", nullable = false, length = 255)
    private String residentialAddress;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leisure> leisures = new ArrayList<>();

    @Override
    @Transient
    public String getTypename() {
        return "TYPE_EMPLOYEE";
    }
}