package by.bsuir.dorm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name="userId")
public class Employee extends User {

}