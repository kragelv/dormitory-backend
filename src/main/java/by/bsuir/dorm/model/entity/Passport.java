package by.bsuir.dorm.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Passport implements Serializable {
    @Column(name = "series", nullable = false, length = 2)
    private String series;

    @Column(name = "number", nullable = false, length = 7)
    private String number;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "issue_place", nullable = false)
    private String issuePlace;
}