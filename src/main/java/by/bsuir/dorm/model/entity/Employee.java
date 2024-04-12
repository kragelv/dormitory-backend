package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Override
    @Transient
    public String getTypename() {
        return "TYPE_EMPLOYEE";
    }
}