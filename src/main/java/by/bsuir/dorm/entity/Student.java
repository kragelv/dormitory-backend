package by.bsuir.dorm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private String isStudentUnionMember;

    @Column(name = "about_college")
    private String aboutCollege;

    @Column(name = "hobbies")
    private String hobbies;

    //TODO: parents
}