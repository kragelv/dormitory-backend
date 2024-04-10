package by.bsuir.dorm.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_employee")
@PrimaryKeyJoinColumn(name="user_id")
public class Employee extends User {
    @Override
    @Transient
    public String getTypename() {
        return "TYPE_EMPLOYEE";
    }
}