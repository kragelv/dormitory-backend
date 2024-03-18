package by.bsuir.dorm.entity;

import by.bsuir.dorm.util.RoleUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "userId")
public class Student extends User {

    @Embedded
    private FullName fullNameBy;

    //TODO: all addresses

    @Column(name = "graduation_year", nullable = false)
    private Integer graduationYear;

    @Column(name = "grade_point_average", columnDefinition = "DECIMAL(3,2)", nullable = false)
    private Double gradePointAverage;

    @Column(name = "has_gold_medal", nullable = false)
    private Boolean hasGoldMedal = false;

    @Column(name = "was_in_military", nullable = false)
    private Boolean wasInMilitary = false;

    @Column(name = "is_student_union_member")
    private Boolean isStudentUnionMember;

    @Column(name = "about_college")
    private String aboutCollege;

    @Column(name = "hobbies")
    private String hobbies;

    //TODO: parents

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "student_studentRoles",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "studentRoles_id"))
    private Set<StudentRole> studentRoles = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return studentRoles
                .stream()
                .map(Role::getName)
                .map(RoleUtil::mapRoleName)
                .collect(Collectors.toSet());
    }
}