package by.bsuir.dorm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
public class StudentRole extends Role {

    @ManyToMany(mappedBy = "studentRoles")
    private List<Student> students = new ArrayList<>();
}