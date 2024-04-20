package by.bsuir.dorm.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class FullName implements Serializable {
    @Column(name = "surname", length = 40)
    private String surname;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "patronymic", length = 40)
    private String patronymic;

    @Override
    public String toString() {
        return Stream.of(surname, name, patronymic)
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" "));
    }
}