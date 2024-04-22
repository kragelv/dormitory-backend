package by.bsuir.dorm.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "residential_address", nullable = false, length = 255)
    private String residentialAddress;

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
    @Setter(AccessLevel.NONE)
    private List<Contract> contracts = new ArrayList<>();

    private void addContract(Contract contract) {
        contracts.add(contract);
        contract.setStudent(this);
    }

    private void removeContract(Contract contract) {
        contracts.remove(contract);
        contract.setStudent(null);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "mother_id")
    private Mother mother;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "father_id")
    private Father father;

    @ManyToMany(mappedBy = "students", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @Setter(AccessLevel.NONE)
    private Set<Leisure> leisures = new LinkedHashSet<>();

    public void addLeisure(Leisure leisure) {
        leisures.add(leisure);
        leisure.getStudents().add(this);
    }

    public void removeLeisure(Leisure leisure) {
        leisures.remove(leisure);
        leisure.getStudents().remove(this);
    }

    @Override
    @Transient
    public String getTypename() {
        return "TYPE_STUDENT";
    }
}