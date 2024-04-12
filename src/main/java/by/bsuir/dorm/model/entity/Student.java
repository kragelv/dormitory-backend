package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_student")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "surname", column = @Column(name = "surname_by", length = 40)),
            @AttributeOverride(name = "name", column = @Column(name = "name_by", length = 40)),
            @AttributeOverride(name = "patronymic", column = @Column(name = "patronymic_by", length = 40))
    })
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

    @OneToMany(
            mappedBy = "student",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    private List<Contract> contracts = new ArrayList<>();

    @Override
    @Transient
    public String getTypename() {
        return "TYPE_STUDENT";
    }

    //TODO: parents
}