package by.bsuir.dorm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FullName {
    @Column(name = "surname", nullable = false, length = 40)
    private String surname;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "patronymic", length = 40)
    private String patronymic;
}